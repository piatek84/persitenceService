package com.coronel.persistenceService.config;

import com.mongodb.client.MongoClients;
import org.ff4j.FF4j;
import org.ff4j.core.Feature;
import org.ff4j.core.FlippingStrategy;
import org.ff4j.mongo.store.FeatureStoreMongo;
import org.ff4j.strategy.time.ReleaseDateFlipStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class FF4jConfig {

    public static final String CONSUMER_ENABLED = "consumer-enabled";
    public static final String SECOND_FEATURE = "second-feature";
    public static final List<String> features = List.of(CONSUMER_ENABLED, SECOND_FEATURE);

    @Bean
    public FF4j ff4j() {
        FeatureStoreMongo featureStore = new FeatureStoreMongo(
                MongoClients.create("mongodb://localhost:27017"), "ff4jDB"
        );
        FF4j ff4j = new FF4j();
        ff4j.setFeatureStore(featureStore);
        for (String f : features) {
            if (!ff4j.exist(f)) {
                Feature feature = new Feature(f);
                //Here we can add a flipping strategy
                ff4j.createFeature(feature);
            }
        }
        return ff4j;
    }
}
//        We can enable the feature after a date
//        FlippingStrategy strategy = new ReleaseDateFlipStrategy("2025-03-23-18:55");
//        feature.setFlippingStrategy(strategy);
