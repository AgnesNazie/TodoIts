package se.Lexicon.models;

import se.Lexicon.Exception.PersonException.InvalidPersonException;
import se.Lexicon.Sequencer.PersonIdSequencer;

import java.util.Objects;

public class Person {
    //create fields
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    // New field to store the credentials (AppUser)
    private AppUser credentials;

    //create parameterized constructor without id
    public Person(String firstName, String lastName, String email, AppUser credentials) {
        this.id = PersonIdSequencer.nextId();
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        this.credentials = credentials;
    }
    // create parameterized constructor with id

    public Person(int id, String firstName, String lastName, String email) {
        this.id = PersonIdSequencer.nextId();
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
    }

    //getter for id

    public int getId() {
        return id;
    }
    //getter for first name

    public String getFirstName() {
        return firstName;
    }
    // setter for first name

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.trim().isEmpty())
            throw new InvalidPersonException("First Name cannot be Null or Empty");
        this.firstName = firstName;
    }
    //getter for last name

    public String getLastName() {
        return lastName;
    }
    // setter for last name

    public void setLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty())
            throw new InvalidPersonException("Last Name cannot be null or empty");
        this.lastName = lastName;
    }
    //getter for email

    public String getEmail() {
        return email;
    }
    // setter for email

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty())
            throw new InvalidPersonException("Email cannot be null or empty");
        this.email = email;
    }
    //getter for credentials

    public AppUser getCredentials() {
        return credentials;
    }
    // setter for credentials

    public void setCredentials(AppUser credentials) {
        if (credentials == null)
            throw new InvalidPersonException("Credentials cannot be null");
        this.credentials = credentials;
    }

    // Overriding the toString() method to exclude credentials
    @Override
    public String toString() {
        return String.format("Person{id=%d, firstName='%s', lastName='%s', email='%s'}", id, firstName, lastName, email);
    }

    // Overriding equals() and hashCode() to exclude credentials

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person person = (Person) obj;
        return id == person.id &&
                Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName) &&
                Objects.equals(email, person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email);
    }

}
