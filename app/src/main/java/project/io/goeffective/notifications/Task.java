package project.io.goeffective.notifications;

import java.util.Date;

public class Task {
    private final int id;
    private final String name;
    private final Date date;

    public Task(int id, String name, Date date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }
}