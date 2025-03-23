package com.coronel.persistenceService.config;

import org.ff4j.FF4j;
import org.ff4j.core.Feature;
import org.ff4j.core.FlippingStrategy;
import org.ff4j.strategy.time.ReleaseDateFlipStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FF4jConfig {

    public static final String CONSUMER_ENABLED = "consumer-enabled";
    @Bean
    public FF4j ff4j(){
        FF4j ff4j = new FF4j();
        Feature consumerEnabledFeature = new Feature(CONSUMER_ENABLED);
//        We can enable the feature after a date
//        consumerEnabledFeature.setEnable(true);
//        FlippingStrategy strategy = new ReleaseDateFlipStrategy("2025-03-23-18:55");
//        consumerEnabledFeature.setFlippingStrategy(strategy);
        ff4j.createFeature(consumerEnabledFeature);
        return ff4j;
    }
}
