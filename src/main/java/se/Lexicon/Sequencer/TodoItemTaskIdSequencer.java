package se.Lexicon.Sequencer;

public class TodoItemTaskIdSequencer {
    private static int currentId = 0;

    public static int nextId() {
        return ++currentId;
    }

    public static int getCurrentId() {
        return currentId;
    }

    public void setCurrentId(int id) {
        currentId = id;
    }
}
