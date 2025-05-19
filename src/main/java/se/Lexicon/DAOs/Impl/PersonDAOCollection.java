package se.Lexicon.DAOs.Impl;

import se.Lexicon.DAOs.PersonDAO;
import se.Lexicon.models.Person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PersonDAOCollection implements PersonDAO {

    //create a list to store app user object
    private final List<Person> personList = new ArrayList<>();

    @Override
    public Person persist(Person person) {
        personList.add(person);
        return person;

    }

    @Override
    public Person findById(Integer id) {
        for (Person person : personList) {
            if (person.getId() == id) {
                return person;
            }
        }
        return null;
    }

    @Override
    public Person findByEmail(String email) {
        for (Person person : personList) {
            if (person.getEmail().equalsIgnoreCase(email)) {
                return person;
            }
        }
        return null;
    }

    @Override
    public Collection<Person> findAll() {
        return new ArrayList<>(personList);
    }

    @Override
    public void remove(Integer id) {
        Person person = findById(id);
        if (person != null) {
            personList.remove(person);
        }

    }
}
