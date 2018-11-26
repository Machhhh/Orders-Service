package com.amach.ordersservice.utils.csvParser;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CsvConfig {

    @Bean
    CsvFacade csvFacade(final CsvService csvService) {
        return new CsvFacade(csvService);
    }
}
