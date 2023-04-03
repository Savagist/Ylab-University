package io.ylab.intensive.lesson05.messagefilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

@Component
public class Db {
    private final DataSource dataSource;
    private final static String TABLE_NAME = "forbidden_words";

    @Autowired
    public Db(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void primaryWorkWithDb() throws FileNotFoundException {
        String create = "CREATE TABLE " + TABLE_NAME + " (id bigserial not null, word varchar)";
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, TABLE_NAME, null);
            if (!tables.next()) {
                PreparedStatement statement = connection.prepareStatement(create);
                statement.executeUpdate();
                System.out.println("Таблица " + TABLE_NAME + " создана");
            } else {
                System.out.println("Таблица " + TABLE_NAME + " уже существует");
            }
            insertIntoTable(connection);
        } catch (SQLException e) {
            System.err.println("Ошибка в создание таблицы: " + e.getMessage());
        }
    }

    private void insertIntoTable(Connection connection) throws SQLException, FileNotFoundException {
        clearTable(connection);
        File file = new File("src" + File.separator + "main" + File.separator + "resources" + File.separator + "18+.txt");
        String sql = "INSERT INTO " + TABLE_NAME + " (word) VALUES (?)";
        try (Scanner scanner = new Scanner(new FileInputStream(file));
             PreparedStatement statement = connection.prepareStatement(sql)) {
            while (scanner.hasNextLine()) {
                statement.setString(1, scanner.nextLine());
                statement.executeUpdate();
            }
        }
    }

    private void clearTable(Connection connection) throws SQLException {
        String sql = "TRUNCATE TABLE " + TABLE_NAME;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        }
    }

}
