package project.io.goeffective.utils.dbobjects;

import java.util.Date;

public class TaskStart {
    private Integer id;
    private Date start;
    private Integer delay;

    public TaskStart(Integer id, Date date, Integer delay){
        this.id = id;
        this.start = date;
        this.delay = delay;
    }

    public Integer getDelay() {
        return delay;
    }

    public Date getStart() {
        return start;
    }

    public Integer getId() {
        return id;
    }
}
