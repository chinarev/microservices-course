package com.example.zipkinserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin.server.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer
public class ZipkinStartApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZipkinStartApplication.class, args);
    }
}