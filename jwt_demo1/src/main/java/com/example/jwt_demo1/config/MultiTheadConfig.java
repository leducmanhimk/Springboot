package com.example.jwt_demo1.config;

import java8.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.logging.Logger;

@EnableAsync
@Configuration

public class MultiTheadConfig {
    CompletableFuture<User>

}
