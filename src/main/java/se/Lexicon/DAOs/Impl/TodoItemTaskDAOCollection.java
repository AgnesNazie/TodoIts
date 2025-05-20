package se.Lexicon.DAOs.Impl;

import se.Lexicon.DAOs.TodoItemTaskDAO;
import se.Lexicon.Exception.TodoItemTaskException.InvalidTodoItemTaskException;
import se.Lexicon.Exception.TodoItemTaskException.TodoItemTaskAlreadyExistsException;
import se.Lexicon.Exception.TodoItemTaskException.TodoItemTaskNotFoundException;
import se.Lexicon.models.TodoItemTask;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TodoItemTaskDAOCollection implements TodoItemTaskDAO {
    private final List<TodoItemTask> todoItemTasks = new ArrayList<>();

    @Override
    public TodoItemTask persist(TodoItemTask todoItemTask) {
        if (todoItemTask == null) {
            throw new InvalidTodoItemTaskException("TodoItemTask cannot be null");
        }
        boolean exists = todoItemTasks.stream()
                .anyMatch(task -> task.getId() == todoItemTask.getId());

        if (exists) {
            throw new TodoItemTaskAlreadyExistsException(todoItemTask.getId());
        }
        todoItemTasks.add(todoItemTask);
        return todoItemTask;
    }

    @Override
    public TodoItemTask findById(Integer id) {
        if (id == null) {
            throw new InvalidTodoItemTaskException("ID cannot be null");
        }

        return todoItemTasks.stream()
                .filter(task -> task.getId() == id)
                .findFirst()
                .orElseThrow(() -> new TodoItemTaskNotFoundException(id));
    }

    @Override
    public Collection<TodoItemTask> findAll() {
        return new ArrayList<>(todoItemTasks);
    }

    @Override
    public Collection<TodoItemTask> findByAssignedStatus(boolean assigned) {
        List<TodoItemTask> result = todoItemTasks.stream()
                .filter(task -> task.isAssigned() == assigned)
                .collect(Collectors.toList());
        if (result.isEmpty()) {
            throw new TodoItemTaskNotFoundException(assigned);
        }
        return result;
    }

    @Override
    public Collection<TodoItemTask> findByPersonId(Integer personId) {
        if (personId == null) {
            throw new InvalidTodoItemTaskException("Person ID cannot be null");
        }
        List<TodoItemTask> result = todoItemTasks.stream()
                .filter(task -> task.getAssignee() != null && task.getAssignee().getId() == personId)
                .collect(Collectors.toList());
        if (result.isEmpty()) {
            throw new TodoItemTaskNotFoundException("No TodoItemTasks found for person ID: " + personId);
        }
        return result;
    }

    @Override
    public void remove(Integer id) {

        TodoItemTask task = findById(id);
        todoItemTasks.remove(task);
    }
}
