package io.ylab.intensive.lesson05.eventsourcing.db;

import com.rabbitmq.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
public class MessageHandler {
    private static final String EXCHANGE = "exc";
    private static final String QUEUE = "main";
    private static final String KEY = "key";

    private final DbOperations dbOperations;

    @Autowired
    public MessageHandler(ConnectionFactory connectionFactory, DbOperations dbOperations) throws IOException, TimeoutException {
        this.dbOperations = dbOperations;
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.DIRECT);
            channel.queueDeclare(QUEUE, true, false, false, null);
            channel.queueBind(QUEUE, EXCHANGE, KEY);
            System.out.println("Начинаем слушать");
            listener(channel);
        }
    }


    private void listener(Channel channel) throws IOException, TimeoutException {
        while (!Thread.currentThread().isInterrupted()) {
            GetResponse message = channel.basicGet(QUEUE, true);
            if (message != null) {
                String received = new String(message.getBody());
                if (received.contains("name")) {
                    dbOperations.add(received);
                } else {
                    dbOperations.remove(received);
                }
            }
        }
    }

}
