package se.Lexicon.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.Lexicon.DAOs.TodoItemTaskDAO;
import se.Lexicon.models.TodoItemTask;

import java.util.Collection;
@Component

public class TodoItemTaskService {
    private final TodoItemTaskDAO todoItemTaskDAO;

    @Autowired
    public TodoItemTaskService(TodoItemTaskDAO todoItemTaskDAO) {
        this.todoItemTaskDAO = todoItemTaskDAO;
    }

    public TodoItemTask persist(TodoItemTask task) {
        return todoItemTaskDAO.persist(task);
    }

    public TodoItemTask findById(Integer id) {
        return todoItemTaskDAO.findById(id);
    }

    public Collection<TodoItemTask> findAll() {
        return todoItemTaskDAO.findAll();
    }

    public Collection<TodoItemTask> findByAssignedStatus(boolean assigned) {
        return todoItemTaskDAO.findByAssignedStatus(assigned);
    }

    public Collection<TodoItemTask> findByPersonId(Integer personId) {
        return todoItemTaskDAO.findByPersonId(personId);
    }

    public void remove(Integer id) {
        todoItemTaskDAO.remove(id);
    }
}
