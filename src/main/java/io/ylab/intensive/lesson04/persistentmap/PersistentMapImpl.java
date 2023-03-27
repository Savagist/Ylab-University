package io.ylab.intensive.lesson04.persistentmap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 * Класс, методы которого надо реализовать
 */
public class PersistentMapImpl implements PersistentMap {

    private final DataSource dataSource;
    private String name;

    public PersistentMapImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void init(String name) {
        this.name = name;
    }

    @Override
    public boolean containsKey(String key) throws SQLException {
        String sql = "SELECT COUNT(*) FROM persistent_map WHERE map_name = ? AND key = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, key);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt(1) > 0;
            }
        }
    }

    @Override
    public List<String> getKeys() throws SQLException {
        String sql = "SELECT key FROM persistent_map WHERE map_name = ?";
        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<String> keys = new ArrayList<>();
                while (resultSet.next()) {
                    keys.add(resultSet.getString(1));
                }
                return keys;
            }
        }
    }

    @Override
    public String get(String key) throws SQLException {
        String sql = "SELECT value FROM persistent_map WHERE map_name = ? AND key = ?";
        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, key);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString(1);
                }
                return null;
            }
        }
    }

    @Override
    public void remove(String key) throws SQLException {
        String sql = "DELETE FROM persistent_map WHERE map_name = ? AND key = ?";
        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, key);
            statement.executeUpdate();
        }
    }

    @Override
    public void put(String key, String value) throws SQLException {
        if (containsKey(key)) {
            String delete_sql = "DELETE FROM persistent_map WHERE map_name = ? AND key = ?";
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(delete_sql)) {
                statement.setString(1, name);
                statement.setString(2, key);
                statement.executeUpdate();
            }
        }
        String sql = "INSERT INTO persistent_map (map_name, key, value) VALUES (?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, key);
            statement.setString(3, value);
            statement.executeUpdate();
        }

    }

    @Override
    public void clear() throws SQLException {
        String sql = "DELETE FROM persistent_map WHERE map_name = ?";
        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.executeUpdate();
        }
    }


}
