package com.amach.coreServices.request;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RequestConfig {

    @Bean
    RequestFacade requestFacade(final RequestService reqS) {
        return new RequestFacade(reqS);
    }
}
