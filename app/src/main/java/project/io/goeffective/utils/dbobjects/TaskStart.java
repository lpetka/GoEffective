package project.io.goeffective.utils.dbobjects;

import java.io.Serializable;
import java.util.Date;

public class TaskStart implements Serializable{
    private Date start;
    private Integer delay;

    public TaskStart(Date date, Integer delay){
        this.start = date;
        this.delay = delay;
    }

    public Integer getDelay() {
        return delay;
    }

    public Date getStart() {
        return start;
    }
}
