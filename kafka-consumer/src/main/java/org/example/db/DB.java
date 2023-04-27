package org.example.db;

import com.github.javafaker.Faker;
import com.my.data.Dog;
import com.my.data.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class DB {

    @Autowired
    Connection connection;

    public void save(final Dog dog, int offset){

        try {
            Faker faker = new Faker();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO dump1 (ID, keyvalue) VALUES (?,?)");

            preparedStatement.setInt(1, offset);
            preparedStatement.setString(2, String.valueOf(dog.getName()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void save(final Student student, int offset){

        try {
            Faker faker = new Faker();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO dump (ID, keyname, keyvalue) VALUES (?,?,?)");

            preparedStatement.setInt(1, offset);
            preparedStatement.setString(2, String.valueOf(student.getName()));
            preparedStatement.setString(3, faker.gameOfThrones().character());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
