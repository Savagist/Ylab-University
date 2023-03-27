package io.ylab.intensive.lesson04.filesort;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.sql.DataSource;

public class FileSortImpl implements FileSorter {
    private final DataSource dataSource;

    public FileSortImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public File sort(File data, boolean flag) throws IOException, SQLException {
        readFileIntoDb(data, flag);
        List<Long> sortedList = sortData();
        File file;
        if (flag){
            file = new File("src" + File.separator + "main" + File.separator + "resources" + File.separator + "sorted_db_with_batch.txt");
        }else {
            file = new File("src" + File.separator + "main" + File.separator + "resources" + File.separator + "sorted_db_without_batch.txt");
        }
        return writeIntoFile(sortedList, file);
    }

    private void readFileIntoDb(File data, boolean flag) throws IOException, SQLException {
        String clear_sql = "DELETE FROM numbers";
        String sql = "INSERT INTO numbers (val) VALUES (?)";
        try (FileInputStream fis = new FileInputStream(data);
             Scanner scanner = new Scanner(fis);
             Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             PreparedStatement statement1 = connection.prepareStatement(clear_sql)) {
            statement1.executeUpdate();
            if (flag) {
                while (scanner.hasNextLong()) {
                    statement.setLong(1, scanner.nextLong());
                    statement.addBatch();
                }
                statement.executeBatch();
            } else {
                while (scanner.hasNextLong()) {
                    statement.setLong(1, scanner.nextLong());
                    statement.executeUpdate();
                }
            }
        }
    }

    private List<Long> sortData() throws SQLException {
        List<Long> longs = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet set = statement.executeQuery("SELECT val FROM numbers ORDER BY val")) {
            while (set.next()) {
                longs.add(set.getLong(1));
            }
        }
        return longs;
    }

    private File writeIntoFile(List<Long> list, File file) throws FileNotFoundException {
        try (PrintWriter pw = new PrintWriter(file)) {
            for (Long element : list) {
                pw.println(element);
            }
        }
        return file;
    }


}
