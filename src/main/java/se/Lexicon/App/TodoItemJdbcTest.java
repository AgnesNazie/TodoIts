package se.Lexicon.App;

import se.Lexicon.DAOs.Impl.PersonJdbc;
import se.Lexicon.DAOs.Impl.TodoItemJdbc;
import se.Lexicon.Model.Person;
import se.Lexicon.Model.TodoItem;

import java.time.LocalDate;
import java.util.Collection;

public class TodoItemJdbcTest {
    public static void main(String[] args) {
        TodoItemJdbc todoDao = new TodoItemJdbc();
        PersonJdbc personDao = new PersonJdbc();

        //  Create a new TodoItem
        Person assignee = new Person( "Agnes", "Nazie");
        assignee = personDao.create(assignee);
        TodoItem newItem = new TodoItem("Complete Todo Assignment", LocalDate.now().plusDays(3), false, assignee);
        TodoItem createdItem = todoDao.create(newItem);
        System.out.println("Created Item: " + createdItem);

        //  Find by ID
        TodoItem foundItem = todoDao.findById(createdItem.getTodoId());
        System.out.println("Found: " + foundItem);

        //  Find all

        Collection<TodoItem> allItems = todoDao.findAll();
        allItems.forEach(System.out::println);

        //  Update item

        createdItem.setTitle("Updated Title");
        createdItem.setDone(true);
        TodoItem updated = todoDao.update(createdItem);
        System.out.println("Updated Item: " + updated);

        //  Find by Done Status

        Collection<TodoItem> doneItems = todoDao.findByDoneStatus(true);
        doneItems.forEach(System.out::println);

        // Find by Assignee (person ID)
        Collection<TodoItem> byPersonId = todoDao.findByAssignee(assignee.getPersonId());
        byPersonId.forEach(System.out::println);

        //  Find by Assignee (Person object)

        Collection<TodoItem> byPerson = todoDao.findByAssignee(assignee);
        byPerson.forEach(System.out::println);

        //  Find unassigned items

        Collection<TodoItem> unassigned = todoDao.findUnassigned();
        unassigned.forEach(System.out::println);

        //  Delete item

        boolean deleted = todoDao.deleteById(createdItem.getTodoId());
        System.out.println("Deleted: " + deleted);
    }
}
