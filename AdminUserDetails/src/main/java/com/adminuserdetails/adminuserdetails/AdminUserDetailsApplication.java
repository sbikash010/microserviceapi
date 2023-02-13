package com.adminuserdetails.adminuserdetails;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class AdminUserDetailsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminUserDetailsApplication.class, args);
    }

}
