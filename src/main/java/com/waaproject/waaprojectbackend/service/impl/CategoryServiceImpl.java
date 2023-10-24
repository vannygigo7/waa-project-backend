package com.waaproject.waaprojectbackend.service.impl;

import com.waaproject.waaprojectbackend.model.Category;
import com.waaproject.waaprojectbackend.repository.CategoryRepository;
import com.waaproject.waaprojectbackend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

}
