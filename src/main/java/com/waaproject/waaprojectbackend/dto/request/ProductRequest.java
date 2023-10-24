package com.waaproject.waaprojectbackend.dto.request;

import com.waaproject.waaprojectbackend.model.Category;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductRequest {

    private String title;
    private String description;
    private List<Category> categories;
    private String imageUrl;
    private boolean released;

    private double startPrice;
    private double depositAmount;
    private LocalDateTime bidDueDateTime;
    private LocalDate payDate;

}
