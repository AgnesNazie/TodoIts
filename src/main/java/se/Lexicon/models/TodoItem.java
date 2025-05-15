package se.Lexicon.models;

import java.time.LocalDate;
import java.util.Objects;

public class TodoItem {
    //create fields
    private int id;
    String title;
    String taskDescription;
    LocalDate deadLine;
    boolean done;
    Person creator;

    //constructor without id

    public TodoItem(String title, String taskDescription, LocalDate deadLine, Person creator) {
        this.id = id;
        setTitle(title);
        setTaskDescription(taskDescription);
        setDeadLine(deadLine);
        setDone(done);
        setCreator(creator);
    }
    //getter for id

    public int getId() {
        return id;
    }
    //getter for title

    public String getTitle() {
        return title;
    }
    //setter for title

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty())
            throw new IllegalArgumentException("Title cannot be null or empty");
        this.title = title;
    }
    //getter for task description

    public String getTaskDescription() {
        return taskDescription;
    }
    //setter for task description

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }
    // getter for deadline

    public LocalDate getDeadLine() {
        return deadLine;
    }
    //setter for deadline

    public void setDeadLine(LocalDate deadLine) {
        if (deadLine == null || deadLine.isBefore(LocalDate.now()))
            throw new IllegalArgumentException("Deadline cannot be null or in the past");

        this.deadLine = deadLine;
    }
    // setter for is done

    public void setDone(boolean done) {
        this.done = done;
    }
    //getter for isDone

    public boolean isDone() {
        return done;
    }
    //getter for creator

    public Person getCreator() {
        return creator;
    }
    //setter for creator

    public void setCreator(Person creator) {
        if (creator == null)
            throw new IllegalArgumentException("Creator cannot be null");
        this.creator = creator;
    }

    //method for is overDue
    public boolean isOverDue() {
        return !done && LocalDate.now().isAfter(deadLine);
    }

    // Override toString to represent all fields except Person object(s)
    @Override
    public String toString() {
        return String.format("TodoItem{id=%d, title='%s', description='%s', deadline=%s, done=%b}",
                id, title, taskDescription, deadLine, done);
    }

    // Override equals to compare all fields except the Person object (creator)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TodoItem todoItem = (TodoItem) obj;
        return done == todoItem.done &&
                id == todoItem.id &&
                Objects.equals(title, todoItem.title) &&
                Objects.equals(taskDescription, todoItem.taskDescription) &&
                Objects.equals(deadLine, todoItem.deadLine);
    }

    // Override hashCode to exclude the Person object (creator)
    @Override
    public int hashCode() {
        return Objects.hash(id, title, taskDescription, deadLine, done);
    }
}
