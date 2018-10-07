package com.amach.coreServices.report;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReportConfig {

    @Bean
    ReportFacade reportFacade(final ReportService repS) {
        return new ReportFacade(repS);
    }
}
