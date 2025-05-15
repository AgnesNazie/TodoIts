package se.Lexicon.models;

public class Person {
    //create fields
    private int id;
    private String firstName;
    private String lastName;
    private String email;

    //create parameterized constructor without id
    public Person(String firstName, String lastName, String email) {
        this.id = id;
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
    }
    // create parameterized constructor with id

    public Person(int id, String firstName, String lastName, String email) {
        this.id = id;
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
            throw new IllegalArgumentException("First Name cannot be Null or Empty");
        this.firstName = firstName;
    }
    //getter for last name

    public String getLastName() {
        return lastName;
    }
    // setter for last name

    public void setLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty())
            throw new IllegalArgumentException("Last Name cannot be null or empty");
        this.lastName = lastName;
    }
    //getter for email

    public String getEmail() {
        return email;
    }
    // setter for email

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty())
            throw new IllegalArgumentException("Email cannot be null or empty");
        this.email = email;
    }
    // get summary method

    public String getSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("id: ").append(id)
                .append(", Name: ").append(firstName).append(" ").append(lastName)
                .append(", Email: ").append(email);
        return sb.toString();
    }
}
