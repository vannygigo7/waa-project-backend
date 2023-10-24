package com.waaproject.waaprojectbackend.service;

import com.waaproject.waaprojectbackend.model.Category;

import java.util.List;

public interface CategoryService {
    Category findByName(String name);

    List<Category> findAll();
}
