package org.example.consumer;

import com.my.data.Dog;
import com.my.data.Student;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.example.consumer.avro.AvroDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ReceiverConfig {

    @Bean
    public Connection getConnection() {
        Connection conn = null;
        Statement stmt = null;
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (Exception e) {
                System.out.println(e);
            }
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3307/path", "testuser", "testpassword");
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return conn;
    }

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, AvroDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "avro");

        return props;
    }

    @Bean
    public ConsumerFactory<String, Student> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(), new StringDeserializer(),
                new AvroDeserializer<>(Student.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Student> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Student> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());

        return factory;
    }

    @Bean
    public ConsumerFactory<String, Dog> consumerFactoryDog() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(), new StringDeserializer(),
                new AvroDeserializer<>(Dog.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Dog> kafkaListenerContainerFactoryDog() {
        ConcurrentKafkaListenerContainerFactory<String, Dog> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryDog());

        return factory;
    }

}
