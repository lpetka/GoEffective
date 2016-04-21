package project.io.goeffective.models;

import android.support.annotation.NonNull;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class DayModel implements IDayModel {
    private Random random = new Random();

    public DayModel() {
    }

    public List<Task> getTodayTasks() {
        List<Task> todayTasks = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            List<Boolean> randomHistory = getRandomHistory();
            Task task = new Task("Zadanie nr " + random.nextInt(99), randomHistory);
            todayTasks.add(task);
        }
        return todayTasks;
    }

    @NonNull
    public List<Boolean> getRandomHistory() {
        List<Boolean> randomHistory = new LinkedList<>();
        int historyLength = random.nextInt(10) + 1;
        for (int j = 0; j < historyLength; j++) {
            randomHistory.add(random.nextInt(5) != 0);
        }
        return randomHistory;
    }
}
