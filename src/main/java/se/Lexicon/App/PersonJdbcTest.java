package se.Lexicon.App;

import se.Lexicon.DAOs.Impl.PersonJdbc;
import se.Lexicon.Model.Person;

import java.util.Collection;

public class PersonJdbcTest {
    public static void main(String[] args) {
        PersonJdbc dao = new PersonJdbc();

        // 1. Create a person
        Person newPerson = new Person("Agnes", "Nazie");
        Person created = dao.create(newPerson);
        System.out.println("Created: " + created);

        // 2. Find by ID
        Person found = dao.findById(created.getPersonId());
        System.out.println("Found by ID: " + found);

        // 3. Find all
        Collection<Person> all = dao.findAll();
        System.out.println("All Persons:");
        all.forEach(System.out::println);

        // 4. Find by Name
        Collection<Person> byName = dao.findByName("Agnes");
        System.out.println("Found by name:");
        byName.forEach(System.out::println);

        // 5. Update
        created.setLastName("Che");
        Person updated = dao.update(created);
        System.out.println("Updated: " + updated);

        // 6. Find again by ID
        System.out.println("After update: " + dao.findById(created.getPersonId()));
    }
}
