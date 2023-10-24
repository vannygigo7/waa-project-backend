package com.waaproject.waaprojectbackend.service.impl;

import com.waaproject.waaprojectbackend.dto.ProductDTO;
import com.waaproject.waaprojectbackend.dto.request.BidRequest;
import com.waaproject.waaprojectbackend.dto.request.ProductRequest;
import com.waaproject.waaprojectbackend.dto.response.ProductResponse;
import com.waaproject.waaprojectbackend.exception.BadRequestException;
import com.waaproject.waaprojectbackend.exception.NotFoundException;
import com.waaproject.waaprojectbackend.exception.UnauthorizedException;
import com.waaproject.waaprojectbackend.model.*;
import com.waaproject.waaprojectbackend.repository.ProductRepository;
import com.waaproject.waaprojectbackend.service.BidService;
import com.waaproject.waaprojectbackend.service.CategoryService;
import com.waaproject.waaprojectbackend.service.ProductService;
import com.waaproject.waaprojectbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final UserService userService;
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final BidService bidService;

    @Override
    public ProductResponse addNewProduct(long sellerId, ProductRequest productRequest) {
        try {
            Seller seller = (Seller) userService.findById(sellerId).orElseThrow(() -> new NotFoundException("Seller " + sellerId + " not found"));
            Auction auction = Auction.builder()
                    .startPrice(productRequest.getStartPrice())
                    .depositAmount(productRequest.getDepositAmount())
                    .highestBid(productRequest.getStartPrice())
                    .bidDueDateTime(productRequest.getBidDueDateTime())
                    .payDate(productRequest.getPayDate())
                    .numberOfBidders(0)
                    .bids(new ArrayList<>()).build();
            List<Category> categories = productRequest.getCategories().stream()
                    .map(category -> categoryService.findByName(category.getName()))
                    .toList();
            Product newProduct = Product.builder()
                    .title(productRequest.getTitle())
                    .description(productRequest.getDescription())
                    .released(productRequest.isReleased())
                    .imageUrl(productRequest.getImageUrl())
                    .categories(categories)
                    .seller(seller)
                    .auction(auction).build();
            return ProductDTO.getProductResponse(productRepository.save(newProduct));
        } catch (NotFoundException e) {
            throw e;
        }
    }

    @Override
    public List<ProductResponse> getAllAuctioningProducts(String name) {
        if (name != null && !name.isEmpty()) {
            return ProductDTO.getProductResponses(productRepository.findProductsByReleasedTrueAndTitleContainingIgnoreCase(name));
        } else {
            return ProductDTO.getProductResponses(productRepository.findProductsByReleased(true));
        }
    }

    @Override
    public List<ProductResponse> getAllAuctioningProductsNotBySeller(long sellerId, String name) {
        if (name != null && !name.isEmpty()) {
            return ProductDTO.getProductResponses(productRepository.findProductsByReleasedTrueAndSellerIdNotAndTitleContainingIgnoreCase(sellerId, name));
        } else {
            return ProductDTO.getProductResponses(productRepository.findProductsByReleasedTrueAndSellerIdNot(sellerId));
        }
    }

    @Override
    public List<ProductResponse> findProductsBySellerId(long sellerId) {
        return ProductDTO.getProductResponses(productRepository.findProductsBySellerId(sellerId));
    }

    @Override
    public List<ProductResponse> findProductsByReleasedAndSellerId(long sellerId, boolean released) {
        return ProductDTO.getProductResponses(productRepository.findProductsByReleasedAndSellerId(released, sellerId));
    }

    @Override
    public List<ProductResponse> getAllProductsByCustomer(long customerId) {
        return ProductDTO.getProductResponses(productRepository.findProductsByAuctionBidsCustomerId(customerId));
    }

    @Override
    public ProductResponse getProductById(long productId) {
        try {
            return ProductDTO.getProductResponse(productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product " + productId + " not found")));
        } catch (NotFoundException e) {
            throw e;
        }
    }

    @Override
    public ProductResponse updateUnreleasedProductByIdBySeller(long sellerId, long productId, ProductRequest updatedProductRequest) {
        try {
            Product oldProduct = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product " + productId + " not found"));

            if (oldProduct.getSeller().getId() != sellerId) {
                throw new UnauthorizedException("Not allowed to updated other sellers' product");
            }

            if (oldProduct.isReleased()) {
                throw new BadRequestException("Try to update released product");
            }

            //update auction
            Auction auction = oldProduct.getAuction();
            auction.setStartPrice(updatedProductRequest.getStartPrice());
            auction.setDepositAmount(updatedProductRequest.getDepositAmount());
            auction.setHighestBid(updatedProductRequest.getStartPrice());
            auction.setBidDueDateTime(updatedProductRequest.getBidDueDateTime());
            auction.setPayDate(updatedProductRequest.getPayDate());

            //update categories
            List<Category> oldCategories = oldProduct.getCategories();
            oldCategories.removeAll(oldCategories);

            updatedProductRequest.getCategories().stream()
                    .map(category -> categoryService.findByName(category.getName()))
                    .forEach(category -> oldCategories.add(category));

            //update product
            oldProduct.setTitle(updatedProductRequest.getTitle());
            oldProduct.setDescription(updatedProductRequest.getDescription());
            oldProduct.setReleased(updatedProductRequest.isReleased());
            oldProduct.setImageUrl(updatedProductRequest.getImageUrl());
            return ProductDTO.getProductResponse(productRepository.save(oldProduct));

        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public ProductResponse deleteUnreleasedProductByIdBySeller(long sellerId, long productId) {
        Product oldProduct = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product " + productId + " not found"));

        if (oldProduct.isReleased()) {
            throw new BadRequestException("Try to delete released product");
        }

        if (oldProduct.getSeller().getId() != sellerId) {
            throw new BadRequestException("Not allowed to delete other sellers' product");
        }

        productRepository.deleteById(productId);
        return ProductDTO.getProductResponse(oldProduct);
    }

    @Override
    public ProductResponse bidProduct(long customerId, long productId, BidRequest bidRequest) {
        try {
            Customer customer = (Customer) userService.findById(customerId).orElseThrow(() -> new NotFoundException("Customer " + customerId + " not found"));
            Wallet wallet = customer.getWallet();
            Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException(("Product " + productId + " not found")));
            Auction auction = product.getAuction();
            LocalDateTime now = LocalDateTime.now();

            boolean isCusHasNeverBidBefore = bidService.findByCustomerIdAndAuctionId(customerId, auction.getId()).isEmpty();
            boolean isCusHasBidBefore      = !(isCusHasNeverBidBefore);

            //check conditions for bidding eligibility
            if (!product.isReleased()) {
                throw new BadRequestException("Try to bid an unreleased product");
            } else if (product.getSeller().getId() == customerId) {
                throw new BadRequestException("Seller cannot bid their own product");
            } else if (now.isAfter(auction.getBidDueDateTime())) {
                throw new BadRequestException("Cannot bid after due date");
            } else if (bidRequest.getBidAmount() <= auction.getHighestBid()) {
                throw new BadRequestException("Bid amount must be higher than current highest amount");
            } else if (isCusHasNeverBidBefore && wallet.getBalance() < auction.getDepositAmount()) {
                throw new BadRequestException("Not enough balance to bid");
            } else {

                if (isCusHasNeverBidBefore) {
                    //deposit the depositAmount
                    wallet.setBlockedBalance(wallet.getBlockedBalance() + auction.getDepositAmount());
                    wallet.setBalance(wallet.getBalance() - auction.getDepositAmount());
                    userService.save(customer);

                    Integer currentNumOfBidders = auction.getNumberOfBidders();
                    Integer newNumberOfBidders  = (currentNumOfBidders == null || currentNumOfBidders == 0) ? 1 : (currentNumOfBidders + 1);
                    auction.setNumberOfBidders(newNumberOfBidders);
                }

                //set highestBid
                auction.setHighestBid(bidRequest.getBidAmount());

                //create a new bid
                List<Bid> bids = auction.getBids();
                bids.add(Bid.builder()
                        .bidAmount(bidRequest.getBidAmount())
                        .bidDateTime(now)
                        .auction(auction)
                        .customer(customer).build());
                return ProductDTO.getProductResponse(productRepository.save(product));
            }
        } catch (NotFoundException e) {
            throw e;
        }
    }

    @Override
    public List<Product> findByReleasedTrueAndAuctionWinnerIsNullAndAuctionBidDueDateTimeBefore(LocalDateTime bidDueDateTime) {
        return productRepository.findByReleasedTrueAndAuctionWinnerIsNullAndAuctionBidDueDateTimeBefore(bidDueDateTime);
    }
}
