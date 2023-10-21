package com.waaproject.waaprojectbackend.service.impl;

import com.waaproject.waaprojectbackend.model.Role;
import com.waaproject.waaprojectbackend.repository.RoleRepository;
import com.waaproject.waaprojectbackend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

}
