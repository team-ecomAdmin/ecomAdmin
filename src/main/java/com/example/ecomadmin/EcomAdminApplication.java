package com.example.ecomadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
@EntityScan
@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class EcomAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcomAdminApplication.class, args);
    }

}
