package com.coronel.persistenceService.consumer;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("local")
public class KafkaConsumerIT {


    private KafkaConsumer kafkaConsumer;

    @Test
    public void testConsume() {

//        kafkaConsumer.consume("test");
    }




}
