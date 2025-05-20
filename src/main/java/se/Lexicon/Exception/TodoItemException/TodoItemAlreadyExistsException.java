package se.Lexicon.Exception.TodoItemException;

public class TodoItemAlreadyExistsException extends RuntimeException {
    public TodoItemAlreadyExistsException(String message) {
        super(message);
    }
}
