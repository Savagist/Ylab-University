package io.ylab.intensive.lesson05.messagefilter;

import com.rabbitmq.client.*;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

@Component
public class MessageHandler {
    private static final String EXCHANGE = "filter";

    private static final String INPUT_QUEUE = "input";
    private static final String OUTPUT_QUEUE = "output";

    private static final String INPUT_KEY = "in";

    private static final String OUTPUT_KEY = "out";

    private final DataSource dataSource;

    private final ConnectionFactory connectionFactory;

    public MessageHandler(DataSource dataSource, ConnectionFactory connectionFactory) {
        this.dataSource = dataSource;
        this.connectionFactory = connectionFactory;
    }

    private Channel createQueues() throws IOException, TimeoutException {
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.queueDeclare(INPUT_QUEUE, true, false, false, null);
        channel.queueDeclare(OUTPUT_QUEUE, true, false, false, null);
        channel.queueBind(INPUT_QUEUE, EXCHANGE, INPUT_KEY);
        channel.queueBind(OUTPUT_QUEUE, EXCHANGE, OUTPUT_KEY);
        return channel;
    }

    public void handler() throws IOException, TimeoutException, SQLException {
        try (Channel channel = createQueues()) {
            System.out.println("Ожидаем сообщение");
            while (!Thread.currentThread().isInterrupted()) {
                GetResponse message = channel.basicGet(INPUT_QUEUE, true);
                if (message != null) {
                    String received = new String(message.getBody());
                    System.out.println("Принято сообщение в очередь " + INPUT_QUEUE + " : " + received);
                    String censoredMessage = censoring(received);
                    channel.basicPublish(EXCHANGE, OUTPUT_KEY, null, censoredMessage.getBytes());
                    System.out.println("Отправленное сообщение в очередь " + OUTPUT_QUEUE + " : " + censoredMessage);
                }
            }
        }
    }

    private String censoring(String message) throws SQLException {
        StringBuilder censoring_words = new StringBuilder();
        StringBuilder word = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            Character currentSymbol = message.charAt(i);
            if (!stopSymbol(currentSymbol)) {
                word.append(currentSymbol);
            } else {
                if (!word.isEmpty()) {
                    censoring_words.append(checkWord(word.toString()));
                }
                censoring_words.append(currentSymbol);
                word = new StringBuilder();
            }
        }
        if (!word.isEmpty()) {
            censoring_words.append(checkWord(word.toString()));
        }
        return censoring_words.toString();
    }

    private boolean stopSymbol(Character character) {
        return character.equals(' ') || character.equals('!') || character.equals(',') || character.equals('?') || character.equals('.') || character.equals(';') || character.equals('\n') || character.equals('\r');
    }

    private String checkWord(String word) throws SQLException {
        String sql = "SELECT COUNT(*) FROM forbidden_words where word = LOWER(?)";
        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, word);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                if (resultSet.getInt(1) > 0) {
                    return wordCensoring(word);
                } else {
                    return word;
                }
            }
        }
    }


    private String wordCensoring(String word) {
        StringBuilder censoredWord = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            if (i == 0) {
                censoredWord.append(word.charAt(i));
            } else if (i == word.length() - 1) {
                censoredWord.append(word.charAt(i));
            } else {
                censoredWord.append("*");
            }
        }
        return censoredWord.toString();
    }

}

