package com.waaproject.waaprojectbackend.service;

import com.waaproject.waaprojectbackend.model.Role;

public interface RoleService {
    Role findByName(String name);

    Role save(Role role);
}
