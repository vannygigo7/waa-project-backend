package com.waaproject.waaprojectbackend.dto.response;

import com.waaproject.waaprojectbackend.model.Category;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductResponse {

    private long id;
    private String title;
    private String description;
    private String imageUrl;
    private List<Category> categories;
    private boolean released;

    private AuctionResponse auction;

    private long sellerId;

}
