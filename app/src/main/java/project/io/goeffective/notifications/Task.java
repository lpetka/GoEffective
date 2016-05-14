package project.io.goeffective.notifications;

import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable {
    private final int id;
    private final String name;
    private final Date date;

    private boolean isDone;

    public Task(int id, String name, Date date) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.isDone = false;
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

    public boolean isDone() {
        return isDone;
    }

    public void setDone() {
        isDone = true;
    }
}