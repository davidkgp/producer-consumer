package org.example.producer;

import com.my.data.Dog;
import com.my.data.Student;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.producer.avro.AvroSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SenderConfig {

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${student.topic.name}")
    private String studentTopicName;

    @Value("${dog.topic.name}")
    private String dogTopicName;

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, AvroSerializer.class);

        return props;
    }


    @Bean
    public ProducerFactory<String, Student> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public ProducerFactory<String, Dog> producerFactoryDog() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, Dog> kafkaTemplateDog() {
        return new KafkaTemplate<>(producerFactoryDog());
    }

    @Bean
    public KafkaTemplate<String, Student> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public Producer<Student> producerStudent() {
        return new Producer<Student>(studentTopicName);
    }

    @Bean
    public Producer<Dog> producerDog() {
        return new Producer<Dog>(dogTopicName);
    }
}
