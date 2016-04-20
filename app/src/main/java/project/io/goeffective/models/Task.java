package project.io.goeffective.models;

import java.util.LinkedList;
import java.util.List;

public class Task {
    String name;
    List<Boolean> history;

    public Task(String name, List<Boolean> history) {
        this.name = name;
        this.history = history;
    }

    public Task(String name) {
        this.name = name;
        this.history = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public List<Boolean> getHistory() {
        return history;
    }
}
