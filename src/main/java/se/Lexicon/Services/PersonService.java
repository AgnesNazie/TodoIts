package se.Lexicon.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.Lexicon.DAOs.PersonDAO;
import se.Lexicon.models.Person;


import java.util.Collection;
@Component
public class PersonService {
    private final PersonDAO personDAO;

    @Autowired
    public PersonService(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public Person create(Person person) {
        return personDAO.persist(person);
    }

    public Person findById(Integer id) {
        return personDAO.findById(id);
    }
    public Person findByEmail(String email){
        return personDAO.findByEmail(email);
    }

    public Collection<Person> findAll() {
        return personDAO.findAll();
    }

    public void remove(Integer id) {
         personDAO.remove(id);
    }

}
