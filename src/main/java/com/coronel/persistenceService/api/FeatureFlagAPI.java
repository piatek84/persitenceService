package com.coronel.persistenceService.api;

import com.coronel.persistenceService.model.Enabler;
import org.ff4j.FF4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.coronel.persistenceService.config.FF4jConfig.CONSUMER_ENABLED;
import static com.coronel.persistenceService.config.FF4jConfig.SECOND_FEATURE;

@RestController
@RequestMapping("/api")
public class FeatureFlagAPI {
    private final FF4j ff4j;
    private final Logger logger = LoggerFactory.getLogger(FeatureFlagAPI.class);

    public FeatureFlagAPI(FF4j ff4j) {
        this.ff4j = ff4j;
    }

    @PostMapping("/consumer")
    public void enableConsumer(@RequestBody Enabler enabler) {
        if (enabler.isEnabled()) {
            ff4j.enable(CONSUMER_ENABLED);
        } else {
            ff4j.disable(CONSUMER_ENABLED);
        }
        logger.info("**** CONSUMER_ENABLED: {} ****", enabler.isEnabled());
    }

    @PostMapping("/second-feature")
    public void enableSecondFeature(@RequestBody Enabler enabler) {
        if (enabler.isEnabled()) {
            ff4j.enable(SECOND_FEATURE);
        } else {
            ff4j.disable(SECOND_FEATURE);
        }
        logger.info("**** SECOND_FEATURE: {} ****", enabler.isEnabled());
    }

}
