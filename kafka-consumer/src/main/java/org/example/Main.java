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


    @KafkaListener(topics = "${dog.topic.name}")
    public void receiveDog(final Dog dog) {

        LOGGER.info("Dog found : {}", dog);
        db.save(dog);

    }

    @KafkaListener(topics = "${student.topic.name}")
    public void receiveStudent(final Student student) {

        LOGGER.info("Student found : {}", student);
        db.save(student);

    }

}