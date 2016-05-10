package project.io.goeffective.utils.dbobjects;


import java.util.ArrayList;
import java.util.List;

public class Task {
    private final Integer id;
    private String name;
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

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<TaskStart> getTaskStartList() {
        return taskStartList;
    }

    public void addTaskStart(TaskStart taskStart){
        taskStartList.add(taskStart);
    }
}
