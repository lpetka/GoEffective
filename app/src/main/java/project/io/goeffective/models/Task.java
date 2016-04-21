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

    public void toggle() {
        final int size = history.size();
        Boolean lastDayHistory = history.get(size - 1);
        history.set(size - 1, !lastDayHistory);
    }

    public int countDaysInARow() {
        int daysInARow = 0;
        for (Boolean isTaskCompleted : history) {
            if (isTaskCompleted) {
                daysInARow += 1;
            } else {
                daysInARow = 0;
            }
        }
        return daysInARow;
    }
}
