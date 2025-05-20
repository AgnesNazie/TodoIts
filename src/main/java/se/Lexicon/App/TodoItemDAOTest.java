package se.Lexicon.App;

import se.Lexicon.DAOs.Impl.TodoItemDAOJdbcImpl;
import se.Lexicon.DAOs.TodoItemDAO;
import se.Lexicon.models.*;

import java.time.LocalDate;
import java.util.Collection;

public class TodoItemDAOTest {
    public static void main(String[] args) {
        // Initialize the DAO
        TodoItemDAO todoItemDAO = new TodoItemDAOJdbcImpl();

        // Create a new TodoItem
        TodoItem newTodo = new TodoItem(
                "Test DAO",
                "Testing DAO with main method",
                LocalDate.now().plusDays(7),
                false,
                null // no assignee
        );

        System.out.println("\n--- Creating Todo ---");
        TodoItem created = todoItemDAO.create(newTodo);
        System.out.println("Created: " + created);

        int todoId = created.getId();

        // Fetch it by ID
        System.out.println("\n--- Finding Todo by ID ---");
        TodoItem found = todoItemDAO.findById(todoId);
        System.out.println("Found: " + found);

        // Update the TodoItem
        System.out.println("\n--- Updating Todo ---");
        found.setTitle("Updated Title");
        found.setDescription("Updated Description");
        found.setDone(true);
        TodoItem updated = todoItemDAO.update(found);
        System.out.println("Updated: " + updated);

        // Get all todos
        System.out.println("\n--- All Todos ---");
        Collection<TodoItem> allTodos = todoItemDAO.findAll();
        allTodos.forEach(System.out::println);

        // Get todos by done status
        System.out.println("\n--- Done Todos ---");
        Collection<TodoItem> doneTodos = todoItemDAO.findByDoneStatus(true);
        doneTodos.forEach(System.out::println);

        // Delete the todo
        System.out.println("\n--- Deleting Todo ---");
        todoItemDAO.delete(todoId);
        System.out.println("Todo with ID " + todoId + " deleted.");

        // Try to fetch again
        System.out.println("\n--- Verifying Deletion ---");
        try {
            todoItemDAO.findById(todoId);
        } catch (Exception e) {
            System.out.println("Expected error: " + e.getMessage());
        }

        System.out.println("\n--- App Test Complete ---");
    }
}