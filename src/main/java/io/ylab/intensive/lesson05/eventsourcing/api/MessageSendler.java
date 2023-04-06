package io.ylab.intensive.lesson05.eventsourcing.api;


import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
public class MessageSendler {

    private final PersonApi personApi;

    @Autowired
    public MessageSendler(PersonApi personApi) throws IOException, TimeoutException {
        this.personApi = personApi;
    }

    public void send() throws IOException, TimeoutException {
        for (long i = 0; i < 100; i++) {
            System.out.println("Отправляем сообщение на запись " + i + " персону");
            personApi.savePerson(i, "firstName " + i, "lastName " + i, "middleName " + i);
            if (i % 2 == 0) {
                System.out.println("Отправляем сообщение на удаление " + i + " персоны");
                personApi.deletePerson(i);
                System.out.println("Отправляем повторное сообщение на удаление " + i + " персоны");
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
