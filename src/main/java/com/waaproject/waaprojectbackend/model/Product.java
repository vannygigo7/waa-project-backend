package com.waaproject.waaprojectbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    @NotBlank(message = "title is required")
    private String title;
    @Column(nullable = false)
    @NotNull(message = "price is required")
    private double price;

    @ManyToOne
    private Seller seller;
    @ManyToMany
    @JoinTable(name = "category_product")
    private List<Category> categories;
}
