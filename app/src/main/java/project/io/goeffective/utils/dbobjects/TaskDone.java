package project.io.goeffective.utils.dbobjects;


import java.sql.Date;

public class TaskDone {
    private final Integer task_id;
    private final Date date;

    public TaskDone(Integer task_id, Date date){
        this.task_id = task_id;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public Integer getTask_id() {
        return task_id;
    }
}
