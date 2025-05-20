package se.Lexicon.Exception.TodoItemException;

import java.time.LocalDate;

public class TodoItemNotFoundException extends RuntimeException {
    public TodoItemNotFoundException(Integer id) {
        super("TodoItem with ID " + id + " was not found.");
    }

    public TodoItemNotFoundException(String title) {
        super("TodoItem with title  " + title + "  was not found.");
    }
    public TodoItemNotFoundException(LocalDate date, boolean before) {
        super("No TodoItems found with deadline " + (before ? "before" : "after") + ": " + date);
    }
}