package se.Lexicon.Exception.TodoItemTaskException;

public class TodoItemTaskNotFoundException extends RuntimeException {
    public TodoItemTaskNotFoundException(Integer id) {
        super("TodoItemTask with ID " + id + " was not found.");
    }

    public TodoItemTaskNotFoundException(String message) {
        super(message);
    }

    public TodoItemTaskNotFoundException(Integer personId, boolean byPersonId) {
        super("TodoItemTasks for Person ID " + personId + " were not found.");
    }

    public TodoItemTaskNotFoundException(boolean assigned) {
        super("TodoItemTasks with assigned status " + assigned + " were not found.");
    }
}
