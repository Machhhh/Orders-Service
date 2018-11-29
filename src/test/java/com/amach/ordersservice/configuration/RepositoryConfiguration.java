package com.amach.ordersservice.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.amach.ordersservice.client",
        "com.amach.ordersservice.request"})
@EnableJpaRepositories(basePackages = {"com.amach.ordersservice.client",
        "com.amach.ordersservice.request"})
@EnableTransactionManagement
public class RepositoryConfiguration {
}
