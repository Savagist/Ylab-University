package io.ylab.intensive.lesson05.eventsourcing.api;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.ylab.intensive.lesson05.eventsourcing.Person;

public interface PersonApi {
  void deletePerson(Long personId) throws IOException, TimeoutException;

  void savePerson(Long personId, String firstName, String lastName, String middleName) throws TimeoutException, JsonProcessingException;

  Person findPerson(Long personId);

  List<Person> findAll();
}
