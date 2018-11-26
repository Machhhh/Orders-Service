package com.amach.coreServices.utils.xmlParser;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XmlConfig {

    @Bean
    XmlFacade xmlFacade(final XmlService xmlService) {
        return new XmlFacade(xmlService);
    }
}
