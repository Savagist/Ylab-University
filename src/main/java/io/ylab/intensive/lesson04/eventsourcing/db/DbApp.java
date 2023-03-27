package io.ylab.intensive.lesson04.eventsourcing.db;

import java.sql.SQLException;
import javax.sql.DataSource;

import com.rabbitmq.client.*;
import io.ylab.intensive.lesson04.DbUtil;
import io.ylab.intensive.lesson04.RabbitMQUtil;


public class DbApp {

    public static void main(String[] args) throws Exception {
        String queueName = "main";
        String exchangeName = "exc";
        String key = "key";
        DataSource dataSource = initDb();
        ConnectionFactory connectionFactory = initMQ();
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            DbApi dbApi = new DbApiImpl(channel, exchangeName, queueName, key);
            while (!Thread.currentThread().isInterrupted()) {
                GetResponse message = channel.basicGet(queueName, true);
                if (message != null) {
                    String received = new String(message.getBody());
                    if (received.contains("name")) {
                        dbApi.add(received, dataSource);
                    } else {
                        dbApi.remove(received, dataSource);
                    }
                }
            }
        }
    }


    private static ConnectionFactory initMQ() throws Exception {
        return RabbitMQUtil.buildConnectionFactory();
    }

    public static DataSource initDb() throws SQLException {
        String ddl = ""
                + "drop table if exists person;"
                + "create table if not exists person (\n"
                + "person_id bigint primary key,\n"
                + "first_name varchar,\n"
                + "last_name varchar,\n"
                + "middle_name varchar\n"
                + ")";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(ddl, dataSource);
        return dataSource;
    }
}
