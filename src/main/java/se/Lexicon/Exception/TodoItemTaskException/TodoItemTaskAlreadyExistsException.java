package se.Lexicon.Exception.TodoItemTaskException;

public class TodoItemTaskAlreadyExistsException extends RuntimeException {
    public TodoItemTaskAlreadyExistsException(String message) {
        super(message);
    }

    public TodoItemTaskAlreadyExistsException(Integer id) {
        super("TodoItemTask already exists with ID: " + id);
    }
}
