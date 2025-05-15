package se.Lexicon.DAOs;

import se.Lexicon.models.TodoItemTask;

import java.util.Collection;

public interface TodoItemTaskDAO {
    TodoItemDAO persist(TodoItemTask todoItemTask);

    TodoItemTask findById(Integer id);

    Collection<TodoItemTask> findAll();

    Collection<TodoItemTask> findByAssignedStatus(boolean assigned);

    Collection<TodoItemTask> findByPersonId(Integer personId);

    void remove(Integer id);
}
