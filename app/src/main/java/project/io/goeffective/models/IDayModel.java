package project.io.goeffective.models;

import java.util.List;

public interface IDayModel {
    List<Boolean> getRandomHistory();
    List<Task> getTodayTasks();
}
