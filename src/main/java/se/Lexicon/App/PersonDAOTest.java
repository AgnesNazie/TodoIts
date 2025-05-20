package se.Lexicon.App;

import se.Lexicon.DAOs.Impl.PersonDAOJdbcImpl;
import se.Lexicon.DAOs.PersonDAO;
import se.Lexicon.models.Person;

import java.util.Collection;

public class PersonDAOTest {
    public static void main(String[] args) {
        PersonDAO personDao = new PersonDAOJdbcImpl();

        try {

            // 1. Create a new person
            Person newPerson = new Person("Agnes", "Nazie");
            Person savedPerson = personDao.create(newPerson);
            System.out.println(" Created Person: " + savedPerson);

            // 2. Find all people
            Collection<Person> people = personDao.findAll();
            System.out.println(" All People:");
            people.forEach(System.out::println);

            // 3. Find by ID
            int id = savedPerson.getId();
            Person found = personDao.findById(id);
            System.out.println(" Found by ID: " + found);

            // 4. Update the person
            found.setLastName("Che");
            Person updated = personDao.update(found);
            System.out.println("Updated Person: " + updated);

            // 5. Find by name
            System.out.println(" Searching by name 'Alice':");
            personDao.findByName("Alice").forEach(System.out::println);

            // 6. Delete the person
            personDao.delete(updated.getId());
            System.out.println(" Deleted person with ID: " + updated.getId());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
