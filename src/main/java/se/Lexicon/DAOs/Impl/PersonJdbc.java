package se.Lexicon.DAOs.Impl;

import se.Lexicon.DAOs.People;
import se.Lexicon.Exception.DataBaseException;
import se.Lexicon.Model.Person;

import javax.xml.stream.events.DTD;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static se.Lexicon.Utils.DBConnection.getConnection;

public class PersonJdbc implements People {
    @Override
    public Person create(Person person) {
        String sql = " INSERT INTO person ( first_name, last_name) VALUES (?,?) ";
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, person.getFirstName());
                statement.setString(2, person.getLastName());
                int rows = statement.executeUpdate();
                if (rows > 0) {
                    try (ResultSet keys = statement.getGeneratedKeys();) {
                        if (keys.next()) {
                            person.setPersonId(keys.getInt(1));
                        }
                    }
                    connection.commit();
                    System.out.println("Person added successfully");
                } else {
                    connection.rollback();
                    throw new DataBaseException(" Insert failed — no rows affected.");
                }

            }
        } catch (SQLException e) {
            throw new DataBaseException("Error while adding" + e.getMessage(), e);
        }

        return person;
    }

    @Override
    public Collection<Person> findAll() {
        List<Person> personList = new ArrayList<>();
        String sql = "SELECT * FROM person";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                personList.add(new Person(
                        resultSet.getInt("person_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name")
                ));
            }

        } catch (
                SQLException e) {
            throw new DataBaseException("Failed to retrieve Person" + e.getMessage(), e);

        }

        return new ArrayList<>(personList);
    }

    @Override
    public Person findById(int id) {
        String sql = "SELECT * FROM person WHERE person_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Person(
                            resultSet.getInt("person_id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"));
                }

            }
        } catch (SQLException e) {
            throw new DataBaseException("Failed to find person with ID" + id + ": " + e);
        }

        return null;
    }

    @Override
    public Collection<Person> findByName(String name) {
        List<Person> personList = new ArrayList<>();
        String sql = "SELECT *FROM person WHERE first_name = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    personList.add(new Person(
                            resultSet.getInt("person_id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name")));
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Failed to find person with name" + name + e);
        }

        return personList;
    }

    @Override
    public Person update(Person person) {
        String sql = "UPDATE person SET  first_name = ?, last_name = ? WHERE person_id = ?";
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            if (findById(person.getPersonId()) == null) {
                throw new DataBaseException("cannot update person");
            }
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, person.getFirstName());
                statement.setString(2, person.getLastName());
                statement.setInt(3, person.getPersonId());
                int rows = statement.executeUpdate();
                if (rows > 0) {
                    connection.commit();
                    System.out.println("Person updated successfully");
                    return person;
                } else {
                    connection.rollback();
                    return null;
                }
            } catch (SQLException e) {
                connection.rollback();
                throw new DataBaseException("Failed to update person" + e.getMessage() + e);
            }
        } catch (SQLException e) {
            throw new DataBaseException("Database error" + e.getMessage() + e);
        }

    }
}
