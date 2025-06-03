package se.Lexicon.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.Lexicon.DAOs.TodoItemDAO;
import se.Lexicon.models.TodoItem;

import java.time.LocalDate;
import java.util.Collection;
@Component

public class TodoItemService {
    private final TodoItemDAO todoItemDAO;

    @Autowired
    public TodoItemService(TodoItemDAO todoItemDAO) {
        this.todoItemDAO = todoItemDAO;
    }

    public TodoItem persist(TodoItem todoItem) {
        return todoItemDAO.persist(todoItem);
    }

    public TodoItem findById(Integer id) {
        return todoItemDAO.findById(id);
    }

    public Collection<TodoItem> findAll() {
        return todoItemDAO.findAll();
    }

    public Collection<TodoItem> findAllDoneByStatus(boolean done) {
        return todoItemDAO.findAllByDoneStatus(done);
    }

    public Collection<TodoItem> findByTitleContains(String title) {
        return todoItemDAO.findByTitleContains(title);
    }

    public Collection<TodoItem> findByPersonId(Integer personId) {
        return todoItemDAO.findByPersonId(personId);
    }

    public Collection<TodoItem> findByDeadLineBefore(LocalDate date) {
        return todoItemDAO.findByDeadLineBefore(date);
    }

    public Collection<TodoItem> findByDeadLineAfter(LocalDate date) {
        return todoItemDAO.findByDeadLineAfter(date);
    }

    public void remove(Integer id) {
        todoItemDAO.remove(id);
    }
}
