package se.Lexicon.DAOs.Impl;

import se.Lexicon.DAOs.PersonDAO;
import se.Lexicon.Exception.Database.DataNotFoundException;
import se.Lexicon.Exception.Database.DatabaseConnectionException;
import se.Lexicon.Exception.PersonException.InvalidPersonException;
import se.Lexicon.JDBC.DBConnection;
import se.Lexicon.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PersonDAOJdbcImpl implements PersonDAO {
    @Override
    public Person create(Person person) {
        if (person == null) throw new InvalidPersonException("Person cannot be null");

        String sql = "INSERT INTO person (first_name, last_name) VALUES ( ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            conn.setAutoCommit(false); // Start transaction

            stmt.setString(1, person.getFirstName());
            stmt.setString(2, person.getLastName());


            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) throw new SQLException("Creating person failed, no rows affected.");

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    person = new Person(
                            generatedKeys.getInt(1),
                            person.getFirstName(),
                            person.getLastName()
                    );
                } else {
                    throw new SQLException("Creating person failed, no ID obtained.");
                }
            }

            conn.commit(); // Commit transaction
            //Success message
            System.out.println(" Person successfully created: " + person);
            return person;

        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to create person", e);
        }
    }

    @Override
    public Collection<Person> findAll() {
        Collection<Person> people = new ArrayList<>();
        String sql = "SELECT * FROM person";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                people.add(mapResultSetToPerson(rs));
            }

        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to fetch people", e);
        }


        System.out.println(" Retrieved " + people.size() + " person(s) from database.");
        return people;
    }

    @Override
    public Person findById(int id) {
        String sql = "SELECT * FROM person WHERE person_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapResultSetToPerson(rs);
                else throw new DataNotFoundException("Person with ID " + id + " not found");
            }

        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to find person by ID", e);
        }
    }

    @Override
    public Collection<Person> findByName(String name) {
        Collection<Person> people = new ArrayList<>();
        String sql = "SELECT * FROM person WHERE first_name LIKE ? OR last_name LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String pattern = "%" + name + "%";
            stmt.setString(1, pattern);
            stmt.setString(2, pattern);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    people.add(mapResultSetToPerson(rs));
                }
                System.out.println(" Retrieved " + people.size() + " person(s) matching the name: " + name);
            }

        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to search person by name", e);
        }

        return people;
    }

    @Override
    public Person update(Person person) {
        if (person == null) throw new InvalidPersonException("Person cannot be null");

        String sql = "UPDATE person SET first_name = ?, last_name = ? WHERE person_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, person.getFirstName());
            stmt.setString(2, person.getLastName());
            stmt.setInt(3, person.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) throw new DataNotFoundException("No person found with ID " + person.getId());


            System.out.println(" Person with ID " + person.getId() + " was successfully updated.");
            return person;

        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to update person", e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM person WHERE person_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) throw new DataNotFoundException("No person found to delete with ID " + id);

            System.out.println(" Person with ID " + id + " was successfully deleted.");
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to delete person", e);
        }
    }

    // Helper method
    private Person mapResultSetToPerson(ResultSet rs) throws SQLException {
        return new Person(
                rs.getInt("person_id"),
                rs.getString("first_name"),
                rs.getString("last_name")
        );
    }
}
