package com.waaproject.waaprojectbackend.constant;

import lombok.Getter;

@Getter
public enum Profile {

    DEFAULT("DEFAULT");

    private final String name;

    Profile(String name) {
        this.name = name;
    }

}
