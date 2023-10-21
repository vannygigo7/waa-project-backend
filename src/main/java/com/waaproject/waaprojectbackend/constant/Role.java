package com.waaproject.waaprojectbackend.constant;

import lombok.Getter;

@Getter
public enum Role {

    USER("USER"),
    SELLER("SELLER");

    private final String name;

    Role(String name) {
        this.name = name;
    }

}
