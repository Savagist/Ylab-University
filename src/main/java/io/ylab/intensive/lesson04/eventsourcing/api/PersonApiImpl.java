package io.ylab.intensive.lesson04.eventsourcing.api;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import io.ylab.intensive.lesson04.eventsourcing.Person;

import javax.sql.DataSource;

import static io.ylab.intensive.lesson04.eventsourcing.db.DbApp.initDb;

/**
 * Тут пишем реализацию
 */
public class PersonApiImpl implements PersonApi {

    private final DataSource dataSource;
    private final Channel channel;

    private final String key;

    private final String exchangeName;

    public PersonApiImpl(Channel channel, String exchangeName,String queueName, String key) throws SQLException, IOException {
        this.dataSource = initDb();
        this.channel = channel;
        this.exchangeName = exchangeName;
        this.key = key;
        this.channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
        this.channel.queueDeclare(queueName, true, false, false, null);
        this.channel.queueBind(queueName, exchangeName, key);
    }


    @Override
    public void deletePerson(Long personId) {
        String message = "{\"id\":\"" + personId + "\"}";
        try {
            channel.basicPublish(exchangeName, key, null, message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void savePerson(Long personId, String firstName, String lastName, String middleName){
        ObjectMapper objectMapper = new ObjectMapper();
        Person person = new Person(personId, firstName, lastName, middleName);
        try {
            String message = objectMapper.writeValueAsString(person);
            channel.basicPublish(exchangeName, key, null, message.getBytes());
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
    public List<Person> findAll(){
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
        }catch (SQLException e){
            e.printStackTrace();
        }
        return persons;
    }
}
