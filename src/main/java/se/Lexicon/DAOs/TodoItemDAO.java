package se.Lexicon.DAOs;

import se.Lexicon.Model.Person;
import se.Lexicon.Model.TodoItem;

import java.util.Collection;

public interface TodoItemDAO {
    TodoItem create(TodoItem item);

    TodoItem findById(int id);

    Collection<TodoItem> findAll();

    Collection<TodoItem> findByDoneStatus(boolean done);

    Collection<TodoItem> findByAssignee(int personId);

    Collection<TodoItem> findByAssignee(Person person);

    Collection<TodoItem> findUnassigned();

    TodoItem update(TodoItem item);

    boolean deleteById(int id);
}
