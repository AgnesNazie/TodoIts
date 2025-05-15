package se.Lexicon.App;

import se.Lexicon.models.AppUser;
import se.Lexicon.models.Person;
import se.Lexicon.models.TodoItem;
import se.Lexicon.models.TodoItemTask;

import java.time.LocalDate;

import static se.Lexicon.models.AppRole.ROLE_APP_ADMIN;
import static se.Lexicon.models.AppRole.ROLE_APP_USER;

public class TodoItsPartTest {
    public static void main(String[] args) {
        // create a person object
        // Create AppUser credentials
        AppUser creatorCredentials = new AppUser("agnes_user", "password123", ROLE_APP_USER);
        AppUser assigneeCredentials = new AppUser("mehrdad_user", "securePass", ROLE_APP_ADMIN);

        // Creating a Person object
        Person creator = new Person("Agnes", "Nazie", "agnes@example.com", creatorCredentials);
        Person assignee = new Person("Mehrdad", "Javan", "mehrdad@example.com", assigneeCredentials);
        // Creating a TodoItem object
        TodoItem task = new TodoItem("Assignments", "Todo IT 1", LocalDate.now().plusDays(1), creator);
        // Creating a TodoItemTask object
        TodoItemTask todoTask = new TodoItemTask(task, assignee);
        System.out.println("==================================================");

        // Printing the summary
        System.out.println(todoTask);
        System.out.println("==================================================");

        // Changing assignee to null
        todoTask.setAssignee(null);
        System.out.println("After removing assignee: " + todoTask);
        System.out.println("==================================================");

        // Testing validation
        try {
            todoTask.setTodoItem(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("==================================================");
        }
        //  Check credential linkage
        System.out.println("Creator username: " + creator.getCredentials().getUsername());
        System.out.println("==================================================");

        // Test equals() and hashCode()
        Person p1 = new Person("Agnes", "Nazie", "agnes@example.com", creatorCredentials);
        Person p2 = new Person("Agnes", "Nazie", "agnes@example.com", creatorCredentials);
        System.out.println("Are persons equal? " + p1.equals(p2));
        System.out.println("==================================================");

        // Overdue task test
        TodoItem oldTask = new TodoItem("Almost Missed Task", "This is still ok", LocalDate.now().plusDays(1), creator);
        System.out.println("Is overdue? " + oldTask.isOverdue());
        System.out.println("==================================================");

        // Invalid Person test
        try {
            Person invalidPerson = new Person("", "", "", creatorCredentials);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid person test: " + e.getMessage());
            System.out.println("==================================================");
        }

        // Invalid title test
        try {
            task.setTitle("");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid title test: " + e.getMessage());
            System.out.println("==================================================");
        }

    }
}

