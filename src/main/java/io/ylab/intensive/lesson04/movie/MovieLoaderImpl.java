package io.ylab.intensive.lesson04.movie;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.sql.DataSource;

public class MovieLoaderImpl implements MovieLoader {
    private final DataSource dataSource;

    public MovieLoaderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void loadData(File file) throws IOException, SQLException {
        List<Movie> movies = readFile(file);
        queryToDb(dataSource, movies);
    }

    private static List<Movie> readFile(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file);
             Scanner scanner = new Scanner(fis)) {
            List<Movie> movies = new ArrayList<>();
            scanner.nextLine();
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                movies.add(createMovie(line));
            }
            return movies;
        }
    }

    private static Movie createMovie(String line) {
        String[] attributes = line.split(";");
        Movie movie = new Movie();
        movie.setYear(attributes[0].equals("") ? null : Integer.parseInt(attributes[0]));
        movie.setLength(attributes[1].equals("") ? null : Integer.parseInt(attributes[1]));
        movie.setTitle(attributes[2]);
        movie.setSubject(attributes[3]);
        movie.setActors(attributes[4]);
        movie.setActress(attributes[5]);
        movie.setDirector(attributes[6]);
        movie.setPopularity(attributes[7].equals("") ? null : Integer.parseInt(attributes[7]));
        movie.setAwards(attributes[8].equals("") ? null : !attributes[8].equals("No"));
        return movie;
    }

    private static void queryToDb(DataSource dataSource, List<Movie> movies) throws SQLException {
        String sql = "INSERT INTO movie (year, length, title, subject, actors, actress, director, popularity, awards) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            parseMovie(statement, movies);
        }
    }

    private static void parseMovie(PreparedStatement statement, List<Movie> movies) throws SQLException {
        for (Movie movie : movies) {
            if (movie.getYear() == null) {
                statement.setNull(1, Types.INTEGER);
            } else {
                statement.setInt(1, movie.getYear());
            }
            if (movie.getLength() == null) {
                statement.setNull(2, Types.INTEGER);
            } else {
                statement.setInt(2, movie.getLength());
            }
            statement.setString(3, movie.getTitle());
            statement.setString(4, movie.getSubject());
            statement.setString(5, movie.getActors());
            statement.setString(6, movie.getActress());
            statement.setString(7, movie.getDirector());
            if (movie.getPopularity() == null) {
                statement.setNull(8, Types.INTEGER);
            } else {
                statement.setInt(8, movie.getPopularity());
            }
            if (movie.getAwards() == null) {
                statement.setNull(9, Types.BOOLEAN);
            } else {
                statement.setBoolean(9, movie.getAwards());
            }
            statement.executeUpdate();
        }
    }
}
