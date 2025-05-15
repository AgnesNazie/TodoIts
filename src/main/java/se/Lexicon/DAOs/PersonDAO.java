package se.Lexicon.DAOs;

import se.Lexicon.models.Person;

import java.util.Collection;

public interface PersonDAO {
    Person persist(Person person);

    Person findById(Integer id);

    Person findByEmail(String email);

    Collection<Person> findAll();

    void remove(Integer id);

}
