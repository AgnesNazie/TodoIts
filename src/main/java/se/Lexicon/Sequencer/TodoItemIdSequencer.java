package se.Lexicon.Sequencer;

public class TodoItemIdSequencer {
    private static int currentId = 0;

    public static int nextId() {
        return ++currentId;
    }

    public static int getCurrentId() {
        return currentId;
    }

    public void SetCurrentId(int id) {
        currentId = id;

    }
}
