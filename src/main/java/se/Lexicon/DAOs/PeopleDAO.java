package se.Lexicon.DAOs;

import se.Lexicon.Model.Person;

import java.util.Collection;

public interface PeopleDAO {
    Person create(Person person);

    Collection<Person> findAll();

    Person findById(int id);

    Collection<Person> findByName(String name);

    Person update(Person person);

    boolean deleteById(int id);
}
