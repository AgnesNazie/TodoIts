package se.Lexicon.App;

import se.Lexicon.DAOs.Impl.TodoItemDAOCollection;
import se.Lexicon.DAOs.TodoItemDAO;
import se.Lexicon.models.AppRole;
import se.Lexicon.models.AppUser;
import se.Lexicon.models.Person;
import se.Lexicon.models.TodoItem;

import java.time.LocalDate;
import java.util.Collection;

public class TodoItemDAOTest {
    public static void main(String[] args) {


        TodoItemDAOCollection dao = new TodoItemDAOCollection();

        // Create AppUser and Person
        AppUser appUser = new AppUser("agnes_user", "password123", AppRole.ROLE_APP_USER);
        Person person = new Person("Agnes", "Nazie", "Agnes@gmail.com", appUser);

        // Task with earlier deadline (to be returned by findByDeadLineBefore)
        TodoItem earlyDeadlineTask = new TodoItem("Early Task", "Deadline soon", LocalDate.now().plusDays(1), person);
        dao.persist(earlyDeadlineTask);

        // Task with later deadline (to be excluded by findByDeadLineBefore)
        TodoItem laterDeadlineTask = new TodoItem("Later Task", "Deadline later", LocalDate.now().plusDays(5), person);
        dao.persist(laterDeadlineTask);

        // Additional tasks
        TodoItem task1 = new TodoItem("Test Task 1", "Description 1", LocalDate.now().plusDays(2), person);
        dao.persist(task1);

        TodoItem task2 = new TodoItem("Test Task 2", "Description 2", LocalDate.now().plusDays(3), person);
        dao.persist(task2);

        // --- Test: findById
        TodoItem foundTask1 = dao.findById(task1.getId());
        System.out.println("Test persist and findById: " + (foundTask1 != null && foundTask1.getId() == task1.getId()));

        // --- Test: findAll
        Collection<TodoItem> allTasks = dao.findAll();
        System.out.println("Test findAll: " + (allTasks.size() == 4)); // Updated count to 4

        // --- Test: findByDoneStatus (all default to false)
        Collection<TodoItem> doneTasks = dao.findAllByDoneStatus(false);
        System.out.println("Test findByDoneStatus: " + (doneTasks.size() == 4));

        // --- Test: findByTitleContains
        Collection<TodoItem> tasksWithTitle = dao.findByTitleContains("Test");
        System.out.println("Test findByTitleContains: " + (tasksWithTitle.size() == 2));

        // --- Test: findByPersonId
        Collection<TodoItem> personTasks = dao.findByPersonId(person.getId());
        System.out.println("Test findByPersonId: " + (personTasks.size() == 4));

        // --- Test: findByDeadLineBefore (+3 days should include early and task1)
        Collection<TodoItem> beforeDeadlineTasks = dao.findByDeadLineBefore(LocalDate.now().plusDays(3));
        System.out.println("Test findByDeadLineBefore: " + (beforeDeadlineTasks.size() == 2)); // Should be early and task1

        // --- Test: findByDeadLineAfter (today — all 4 should be returned)
        Collection<TodoItem> upcomingTasks = dao.findByDeadLineAfter(LocalDate.now());
        System.out.println("Test findByDeadLineAfter: " + (upcomingTasks.size() == 4));

        // --- Test: remove
        dao.remove(task1.getId());
        try {
            dao.findById(task1.getId());
            System.out.println("Test remove: false (should have thrown exception)");
        } catch (Exception e) {
            System.out.println("Test remove: true (exception thrown as expected)");
        }

    }
}
