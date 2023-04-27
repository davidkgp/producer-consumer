package org.example;

import com.my.data.Dog;
import com.my.data.Student;
import org.example.db.DB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@EnableKafka
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}

@Component
class Trigger {

    private static final Logger LOGGER = LoggerFactory.getLogger(Trigger.class);

    @Autowired
    DB db;


    @KafkaListener(topics = "${dog.topic.name}", containerFactory = "kafkaListenerContainerFactoryDog")
    public void receiveDog(@Payload final Dog dog, @Header(KafkaHeaders.OFFSET) int offset, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition, Acknowledgment acknowledgment) {

        System.out.println("Dog : offset: " + offset + " partition: " + partition);
        db.save(dog, offset);
        acknowledgment.acknowledge();
        //acknowledgment.nack(10); //sleep time should be less than max.poll.interval.ms

    }

    @KafkaListener(topics = "${student.topic.name}", containerFactory = "kafkaListenerContainerFactory")
    public void receiveStudent(@Payload final Student student, @Header(KafkaHeaders.OFFSET) int offset, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition, Acknowledgment acknowledgment) {

        System.out.println("Student : offset: " + offset + " partition: " + partition);
        db.save(student, offset);
        acknowledgment.acknowledge();
        //acknowledgment.nack(10); //sleep time should be less than max.poll.interval.ms

    }

}