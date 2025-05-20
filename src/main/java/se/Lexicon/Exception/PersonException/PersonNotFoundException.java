package se.Lexicon.Exception.PersonException;

public class PersonNotFoundException extends RuntimeException {
    // custom to use find by id since data types are different
    public PersonNotFoundException(Integer id) {
        super("Person with ID " + id + " was not found.");
    }

    //custom to use for find by email since data types are different

    public PersonNotFoundException(String email) {
        super("Person with email  " + email + "  was not found.");
    }
}
