package project.io.goeffective.utils.dbobjects;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Task implements Serializable{
    private final Integer id;
    private String name;
    private boolean notification = false;
    private String note;
    private List<TaskStart> taskStartList = new ArrayList<>();

    public Task(Integer id, String name, List<TaskStart> list){
        this.id = id;
        this.name = name;
        this.taskStartList = list;
    }

    public Task(String name){
        this.id = -1;
        this.name = name;
    }

    public Task(Integer id, String name, List<TaskStart> list, String note, boolean notification){
        this.id = id;
        this.name = name;
        this.taskStartList = list;
        this.note = note;
        this.notification = notification;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TaskStart> getTaskStartList() {
        return taskStartList;
    }

    public void addTaskStart(TaskStart taskStart){
        taskStartList.add(taskStart);
    }

    public void addEveryWeek(Date date) {
        TaskStart taskStart = new TaskStart(date, 7);
        this.addTaskStart(taskStart);
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isNotification() {
        return notification;
    }

    public void setNotification(boolean notification) {
        this.notification = notification;
    }
}
