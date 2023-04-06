package io.ylab.intensive.lesson05.eventsourcing.api;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;


import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class PersonApiImpl implements PersonApi {
    private static final String EXCHANGE = "exc";
    private static final String QUEUE = "main";
    private static final String KEY = "key";


    private final DataSource dataSource;
    private final ConnectionFactory connectionFactory;


    @Autowired
    public PersonApiImpl(DataSource dataSource, ConnectionFactory connectionFactory) throws  IOException, TimeoutException {
        this.dataSource = dataSource;
        this.connectionFactory = connectionFactory;
        try (com.rabbitmq.client.Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.DIRECT);
            channel.queueDeclare(QUEUE, true, false, false, null);
            channel.queueBind(QUEUE, EXCHANGE, KEY);
        }
    }


    @Override
    public void deletePerson(Long personId) throws TimeoutException {
        String message = "{\"id\":\"" + personId + "\"}";
        try (com.rabbitmq.client.Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.basicPublish(EXCHANGE, KEY, null, message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void savePerson(Long personId, String firstName, String lastName, String middleName) throws TimeoutException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Person person = new Person(personId, firstName, lastName, middleName);
        String message = objectMapper.writeValueAsString(person);
        try (com.rabbitmq.client.Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.basicPublish(EXCHANGE, KEY, null, message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Person findPerson(Long personId) {
        String sql = "SELECT * FROM person WHERE person_id = ?";
        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, personId);
            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    Person person = new Person();
                    person.setId(set.getLong("person_id"));
                    person.setName(set.getString("first_name"));
                    person.setLastName(set.getString("last_name"));
                    person.setMiddleName(set.getString("middle_name"));
                    return person;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Person> findAll() {
        List<Person> persons = new ArrayList<>();
        String sql = "SELECT * FROM person";
        try (java.sql.Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet set = statement.executeQuery(sql)) {
            while (set.next()) {
                Person person = new Person();
                person.setId(set.getLong("person_id"));
                person.setName(set.getString("first_name"));
                person.setLastName(set.getString("last_name"));
                person.setMiddleName(set.getString("middle_name"));
                persons.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persons;
    }
}

