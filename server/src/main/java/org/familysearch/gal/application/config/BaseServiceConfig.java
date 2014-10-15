package org.familysearch.gal.application.config;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.ObjectMapper;
import org.familysearch.engage.foundation.services.ServiceFactory;
import org.familysearch.engage.foundation.services.impl.DefaultServiceFactory;
import org.familysearch.engage.foundation.util.impl.FSLinkBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.familysearch.gal.shared.rest.exception.DefaultExceptionMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"org.familysearch.gal.application.service.impl",
                               "org.familysearch.gal.application.rest.api.endpoints",
                               "org.familysearch.gal.application.service.mappers",
                               "org.familysearch.gal.application.rest.api.support"})

public abstract class BaseServiceConfig {
    @Bean
    public org.codehaus.jackson.map.ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
    
    @Bean
    @Scope(value = "singleton")
    public DefaultExceptionMapper exceptionMapper(){
        return new DefaultExceptionMapper();
    }

    @Bean
    public FSLinkBuilderFactory linkBuilderFactory() {
        return new FSLinkBuilderFactory();
    }

    @Bean
    ServiceFactory foundationServiceLocator() {
        final DefaultServiceFactory defaultServiceFactory = new DefaultServiceFactory();
        defaultServiceFactory.setFeaturesResourceLocation("config/local/features.properties");
        return defaultServiceFactory;
    }
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
