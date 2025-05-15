package se.Lexicon.DAOs;

import se.Lexicon.models.TodoItem;

import java.time.LocalDate;
import java.util.Collection;

public interface TodoItemDAO {
    TodoItem persist(TodoItem todoItem);

    TodoItem findById(Integer id);

    Collection<TodoItem> findAll();

    Collection<TodoItem> findAllByDone(boolean done);

    Collection<TodoItem> findByTitleContains(String title);

    Collection<TodoItem> findByPersonId(Integer personId);

    Collection<TodoItem> findByDeadLineBefore(LocalDate date);

    Collection<TodoItem> findByDeadLineAfter(LocalDate date);

    void remove(Integer id);

}
