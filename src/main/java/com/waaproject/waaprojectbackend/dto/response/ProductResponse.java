package com.waaproject.waaprojectbackend.dto.response;

import com.waaproject.waaprojectbackend.model.Auction;
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
    private List<Category> categories;
    private boolean released;

    private Auction auction;

    private long sellerId;

}
