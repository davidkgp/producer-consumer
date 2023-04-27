package org.example.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

public class Producer<T> {

    public Producer(final String avroTopic, final KafkaTemplate<String, T> kafkaTemplate) {
        this.avroTopic = avroTopic;
        this.kafkaTemplate = kafkaTemplate;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    private String avroTopic;

    private KafkaTemplate<String, T> kafkaTemplate;

    public void send(T data) {
        LOGGER.info("sending data = '{}' ", data);
        kafkaTemplate.send(avroTopic, data);
    }
}
