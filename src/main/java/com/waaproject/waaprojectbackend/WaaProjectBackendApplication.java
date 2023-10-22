package com.waaproject.waaprojectbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WaaProjectBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(WaaProjectBackendApplication.class, args);
    }

}
