package project.io.goeffective.utils.dbobjects;


import java.sql.Date;

public class TaskStart {
    private Integer id;
    private Date start;
    private Integer delay;

    public Integer getDelay() {
        return delay;
    }

    public Date getStart() {
        return start;
    }
}
