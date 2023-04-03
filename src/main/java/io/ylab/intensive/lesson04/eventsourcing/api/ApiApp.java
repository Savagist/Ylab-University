package io.ylab.intensive.lesson04.eventsourcing.api;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.RabbitMQUtil;
import io.ylab.intensive.lesson04.eventsourcing.Person;

public class ApiApp {
    public static void main(String[] args) throws Exception {
        String exchangeName = "exc";
        String queueName = "main";
        String key = "key";
        ConnectionFactory connectionFactory = initMQ();
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            PersonApi personApi = new PersonApiImpl(channel, exchangeName, queueName, key);
            for (long i = 0; i < 100; i++) {
                System.out.println("Отправляем сообщение на запись " + i + " персону");
                personApi.savePerson(i, "firstName " + i, "lastName " + i, "middleName " + i);
                if (i % 2 == 0) {
                    System.out.println("Отправляем сообщение на удаление "+ i +" персоны");
                    personApi.deletePerson(i);
                    System.out.println("Отправляем повторное сообщение на удаление "+ i +" персоны");
                    personApi.deletePerson(i);
                    System.out.println("!!!ДОЛЖНА ВЫБРОСИТСЯ ОШИБКА НА ПРИЕМНИКЕ!!!");
                }
                System.out.println("Выводим персону под индексом " + (i / 10) + " если на текущий момент он есть в бд: " + personApi.findPerson(i / 10));
                System.out.println("Отправляем сообщение на обновление " + i + " персоны");
                personApi.savePerson(i, "updateFirstName " + i, "updateLastName " + i, "updateMiddleName " + i);
            }
            System.out.println("Все персоны на текущий момент");
            for (Person person : personApi.findAll()) {
                System.out.println(person);
            }
        }
    }

    private static ConnectionFactory initMQ() throws Exception {
        return RabbitMQUtil.buildConnectionFactory();
    }
}
