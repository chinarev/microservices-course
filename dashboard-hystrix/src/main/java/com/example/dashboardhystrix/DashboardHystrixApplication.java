package com.example.dashboardhystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
public class DashboardHystrixApplication {

    public static void main(String[] args) {
        SpringApplication.run(DashboardHystrixApplication.class, args);
    }
}
