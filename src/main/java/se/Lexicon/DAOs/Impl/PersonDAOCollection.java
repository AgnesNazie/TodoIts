package se.Lexicon.DAOs.Impl;

import se.Lexicon.DAOs.PersonDAO;
import se.Lexicon.Exception.PersonException.InvalidPersonException;
import se.Lexicon.Exception.PersonException.PersonAlreadyExistsException;
import se.Lexicon.Exception.PersonException.PersonNotFoundException;
import se.Lexicon.models.Person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PersonDAOCollection implements PersonDAO {

    //create a list to store app user object
    private final List<Person> personList = new ArrayList<>();

    @Override
    public Person persist(Person person) {
        if (person == null || person.getEmail() == null || person.getEmail().trim().isEmpty()) {
            throw new InvalidPersonException("Invalid Person: email must not be null or empty.");
        }

        boolean exists = personList.stream()
                .anyMatch(p -> p.getEmail().equalsIgnoreCase(person.getEmail()));
        if (exists) {
            throw new PersonAlreadyExistsException("Person with email " + person.getEmail() + " already exists.");
        }
        personList.add(person);
        return person;

    }

    @Override
    public Person findById(Integer id) {
        return personList.stream()
                .filter(person -> person.getId() == id)
                .findFirst()
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    @Override
    public Person findByEmail(String email) {
        return personList.stream()
                .filter(person -> person.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElseThrow(() -> new PersonNotFoundException(email));
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
