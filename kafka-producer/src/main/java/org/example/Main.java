package org.example;

import com.github.javafaker.Faker;
import com.my.data.Dog;
import com.my.data.Student;
import org.example.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Locale;

@SpringBootApplication
@EnableScheduling
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}

@Component
class Trigger {

    @Autowired
    private Producer<Student> producerStudent;
    @Autowired
    private Producer<Dog> producerDog;

    @Scheduled(fixedRate = 500)
    public void start() {
        Faker faker = new Faker(new Locale("en-GB"));
        Student student = new Student(faker.funnyName().name(), faker.number().randomDigit());
        producerStudent.send(student);

        Dog dog = new Dog(faker.animal().name());
        producerDog.send(dog);
    }

}