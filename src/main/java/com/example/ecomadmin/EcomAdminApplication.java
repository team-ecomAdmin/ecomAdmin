package com.example.ecomadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EcomAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcomAdminApplication.class, args);
    }

}
