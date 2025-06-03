package se.Lexicon.DAOs.Impl;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import se.Lexicon.DAOs.TodoItemDAO;
import se.Lexicon.Exception.TodoItemException.InvalidTodoItemException;
import se.Lexicon.Exception.TodoItemException.TodoItemAlreadyExistsException;
import se.Lexicon.Exception.TodoItemException.TodoItemNotFoundException;
import se.Lexicon.models.TodoItem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
@Component(value ="TodoItemDAO")

public class TodoItemDAOCollection implements TodoItemDAO {
    //create a list to store app user object
    private final List<TodoItem> todoItems = new ArrayList<>();

    @Override
    public TodoItem persist(TodoItem todoItem) {

        if (todoItem == null) {
            throw new InvalidTodoItemException("TodoItem cannot be null");
        }
        boolean exists = todoItems.stream()
                .anyMatch(item -> item.getId() == (todoItem.getId()));
        if (exists) {
            throw new TodoItemAlreadyExistsException("TodoItem already exists with ID: " + todoItem.getId());
        }
        todoItems.add(todoItem);
        return todoItem;
    }

    @Override
    public TodoItem findById(Integer id) {
        if (id == null) throw new InvalidTodoItemException("TodoItem ID cannot be null.");

        return todoItems.stream()
                .filter(item -> item.getId() == id)
                .findFirst()
                .orElseThrow(() -> new TodoItemNotFoundException(id));
    }

    @Override
    public Collection<TodoItem> findAll() {
        return new ArrayList<>(todoItems);
    }

    @Override
    public Collection<TodoItem> findAllByDoneStatus(boolean done) {
        List<TodoItem> result = todoItems.stream()
                .filter(item -> item.isDone() == done)
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            throw new TodoItemNotFoundException("No TodoItems found with done status: " + done);
        }

        return result;
    }

    @Override
    public Collection<TodoItem> findByTitleContains(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new InvalidTodoItemException("Title cannot be null or empty.");
        }

        List<TodoItem> result = todoItems.stream()
                .filter(item -> item.getTitle() != null &&
                        item.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            throw new TodoItemNotFoundException(title);
        }

        return result;
    }

    @Override
    public Collection<TodoItem> findByPersonId(Integer personId) {
        if (personId == null) {
            throw new InvalidTodoItemException("Person ID cannot be null.");
        }

        List<TodoItem> result = todoItems.stream()
                .filter(item -> item.getCreator() != null &&
                        item.getCreator().getId() == personId)
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            throw new TodoItemNotFoundException("No TodoItems found for person ID: " + personId);
        }

        return result;
    }

    @Override
    public Collection<TodoItem> findByDeadLineBefore(LocalDate date) {
        if (date == null) {
            throw new InvalidTodoItemException("Date cannot be null.");
        }

        List<TodoItem> result = todoItems.stream()
                .filter(item -> item.getDeadLine() != null &&
                        item.getDeadLine().isBefore(date))
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            throw new TodoItemNotFoundException(date, true);
        }

        return result;
    }

    @Override
    public Collection<TodoItem> findByDeadLineAfter(LocalDate date) {
        if (date == null) {
            throw new InvalidTodoItemException("Date cannot be null.");
        }

        List<TodoItem> result = todoItems.stream()
                .filter(item -> item.getDeadLine() != null &&
                        item.getDeadLine().isAfter(date))
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            throw new TodoItemNotFoundException(date, false);
        }

        return result;
    }

    @Override
    public void remove(Integer id) {
        TodoItem item = findById(id);
        todoItems.remove(item);
    }
}

