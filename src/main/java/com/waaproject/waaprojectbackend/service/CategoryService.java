package com.waaproject.waaprojectbackend.service;

import com.waaproject.waaprojectbackend.model.Category;

public interface CategoryService {
    Category findByName(String name);
}
