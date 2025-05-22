package se.Lexicon.DAOs.Impl;

import se.Lexicon.DAOs.TodoItemDAO;
import se.Lexicon.Exception.DataBaseException;
import se.Lexicon.Model.Person;
import se.Lexicon.Model.TodoItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static se.Lexicon.Utils.DBConnection.getConnection;

public class TodoItemJdbc implements TodoItemDAO {
    // CREATE a new TodoItem
    @Override
    public TodoItem create(TodoItem item) {
        String sql = "INSERT INTO todo_item (title, description, deadline, done, assignee_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            connection.setAutoCommit(false);

            statement.setString(1, item.getTitle());
            statement.setString(2, item.getDescription());
            statement.setDate(3, Date.valueOf(item.getDeadline()));
            statement.setBoolean(4, item.isDone());
            if (item.getAssignee() != null) {
                statement.setInt(5, item.getAssignee().getPersonId());
            } else {
                statement.setNull(5, Types.INTEGER);
            }

            int rows = statement.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = statement.getGeneratedKeys()) {
                    if (keys.next()) {
                        item.setTodoId(keys.getInt(1));
                    }
                }
                connection.commit();
                return item;
            } else {
                connection.rollback();
                throw new DataBaseException("Insert failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new DataBaseException("Error creating TodoItem: " + e.getMessage(), e);
        }
    }

    // READ: Find TodoItem by ID
    @Override
    public TodoItem findById(int id) {
        String sql = "SELECT * FROM todo_item WHERE todo_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTodoItem(rs);
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error finding TodoItem by ID: " + id, e);
        }
        return null;
    }

    // READ: Find all TodoItems
    @Override
    public Collection<TodoItem> findAll() {
        List<TodoItem> items = new ArrayList<>();
        String sql = "SELECT * FROM todo_item";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                items.add(mapResultSetToTodoItem(rs));
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error retrieving all TodoItems", e);
        }
        return items;
    }

    // READ: Find TodoItems by done status
    @Override
    public Collection<TodoItem> findByDoneStatus(boolean done) {
        List<TodoItem> items = new ArrayList<>();
        String sql = "SELECT * FROM todo_item WHERE done = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBoolean(1, done);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    items.add(mapResultSetToTodoItem(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error finding TodoItems by done status", e);
        }
        return items;
    }

    //  Find TodoItems by assignee ID
    @Override
    public Collection<TodoItem> findByAssignee(int personId) {
        List<TodoItem> items = new ArrayList<>();
        String sql = "SELECT * FROM todo_item WHERE assignee_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, personId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    items.add(mapResultSetToTodoItem(rs));
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error finding TodoItems by assignee ID", e);
        }
        return items;
    }

    //  Find TodoItems by assignee Person
    @Override
    public Collection<TodoItem> findByAssignee(Person person) {
        return findByAssignee(person.getPersonId());
    }

    //  Find unassigned TodoItems (assignee_id is NULL)
    @Override
    public Collection<TodoItem> findUnassigned() {
        List<TodoItem> items = new ArrayList<>();
        String sql = "SELECT * FROM todo_item WHERE assignee_id IS NULL";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                items.add(mapResultSetToTodoItem(rs));
            }
        } catch (SQLException e) {
            throw new DataBaseException("Error finding unassigned TodoItems", e);
        }
        return items;
    }

    // UPDATE a TodoItem
    @Override
    public TodoItem update(TodoItem item) {
        String sql = "UPDATE todo_item SET title = ?, description = ?, deadline = ?, done = ?, assignee_id = ? WHERE todo_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            connection.setAutoCommit(false);

            statement.setString(1, item.getTitle());
            statement.setString(2, item.getDescription());
            statement.setDate(3, Date.valueOf(item.getDeadline()));
            statement.setBoolean(4, item.isDone());

            if (item.getAssignee() != null) {
                statement.setInt(5, item.getAssignee().getPersonId());
            } else {
                statement.setNull(5, Types.INTEGER);
            }

            statement.setInt(6, item.getTodoId());

            int rows = statement.executeUpdate();
            if (rows > 0) {
                connection.commit();
                return item;
            } else {
                connection.rollback();
                return null;
            }

        } catch (SQLException e) {
            throw new DataBaseException("Error updating TodoItem: " + e.getMessage(), e);
        }
    }

    // DELETE a TodoItem by ID
    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM todo_item WHERE todo_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DataBaseException("Error deleting TodoItem by ID", e);
        }
    }

    // Helper to map a row to a TodoItem
    private TodoItem mapResultSetToTodoItem(ResultSet rs) throws SQLException {
        Person assignee = null;
        int assigneeId = rs.getInt("assignee_id");
        if (!rs.wasNull()) {
            assignee = new Person(assigneeId); // Modify if you need full object
        }

        return new TodoItem(
                rs.getInt("todo_id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getDate("deadline").toLocalDate(),
                rs.getBoolean("done"),
                assignee
        );
    }
}
