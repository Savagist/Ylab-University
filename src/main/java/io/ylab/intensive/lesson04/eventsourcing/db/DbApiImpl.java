package io.ylab.intensive.lesson04.eventsourcing.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import io.ylab.intensive.lesson04.eventsourcing.Person;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static io.ylab.intensive.lesson04.eventsourcing.db.DbApp.initDb;

public class DbApiImpl implements DbApi {

    public DbApiImpl(Channel channel, String exchangeName, String queueName, String key) throws IOException, SQLException {
        DataSource dataSource = initDb();
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, key);
    }


    @Override
    public void add(String query, DataSource dataSource) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Person person = mapper.readValue(query, Person.class);
        String sql = "SELECT * FROM person WHERE person_id = ?";
        String insertSql = "INSERT INTO person (person_id, first_name,last_name, middle_name) VALUES (?,?,?,?)";
        String updateSql = "UPDATE person SET first_name = ?, last_name = ?, middle_name = ? WHERE person_id = ?";
        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             PreparedStatement insertStatement = connection.prepareStatement(insertSql);
             PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
            statement.setLong(1, person.getId());
            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    updateStatement.setString(1, person.getName());
                    updateStatement.setString(2, person.getLastName());
                    updateStatement.setString(3, person.getMiddleName());
                    updateStatement.setLong(4, person.getId());
                    updateStatement.executeUpdate();
                } else {
                    insertStatement.setLong(1, person.getId());
                    insertStatement.setString(2, person.getName());
                    insertStatement.setString(3, person.getLastName());
                    insertStatement.setString(4, person.getMiddleName());
                    insertStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void remove(String query, DataSource dataSource) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(query);
        long id = jsonNode.get("id").asLong();
        String sql = "DELETE FROM person WHERE person_id = ?";
        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted == 0) {
                System.err.println("Удаление не выполнено: данные для personId = " + id + " не найдены.");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
