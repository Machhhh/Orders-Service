package com.amach.coreServices.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ClientConfig {

    @Bean
    ClientFacade clientFacade(final ClientService cS) {
        return new ClientFacade(cS);
    }
}
