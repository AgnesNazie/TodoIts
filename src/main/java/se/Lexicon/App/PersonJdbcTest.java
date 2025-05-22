package se.Lexicon.App;

import se.Lexicon.DAOs.Impl.PersonJdbc;
import se.Lexicon.Model.Person;

import java.util.Collection;

public class PersonJdbcTest {
    public static void main(String[] args) {
        PersonJdbc dao = new PersonJdbc();

        //  Create a person
        Person newPerson = new Person("Agnes", "Nazie");
        Person created = dao.create(newPerson);
        System.out.println("Created: " + created);

        //  Find by ID
        Person found = dao.findById(created.getPersonId());
        System.out.println("Found by ID: " + found);

        //  Find all
        Collection<Person> all = dao.findAll();
        System.out.println("All Persons:");
        all.forEach(System.out::println);

        //  Find by Name
        Collection<Person> byName = dao.findByName("Agnes");
        System.out.println("Found by name:");
        byName.forEach(System.out::println);

        //  Update
        created.setLastName("Che");
        Person updated = dao.update(created);
        System.out.println("Updated: " + updated);

        //  Find again by ID
        System.out.println("After update: " + dao.findById(created.getPersonId()));

        // delete by id
        boolean deleted = dao.deleteById(created.getPersonId());
        System.out.println("Deleted: " + deleted);

    }
}
