package io.ylab.intensive.lesson05.eventsourcing.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DbOperations {
    private final DataSource dataSource;

    @Autowired
    public DbOperations(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void add(String query) throws JsonProcessingException {
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

    public void remove(String query) throws JsonProcessingException {
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
