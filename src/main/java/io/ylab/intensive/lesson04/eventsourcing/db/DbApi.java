package io.ylab.intensive.lesson04.eventsourcing.db;

import com.fasterxml.jackson.core.JsonProcessingException;

import javax.sql.DataSource;

public interface DbApi {

    void add(String query, DataSource dataSource) throws JsonProcessingException;

    void remove(String query, DataSource dataSource) throws JsonProcessingException;
}
