package org.kickass.lordofpomelo.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "bigint(20)")
    private long id;
    @Column(columnDefinition = "bigint(20)")
    private long playerId;
    @Column(columnDefinition = "bigint(20)")
    private long kindId;
    @Column(columnDefinition = "smallint(6)")
    private int taskState;
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Column(columnDefinition = "varchar(1000)")
    private String taskData;
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getPlayerId() {
        return playerId;
    }
    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }
    public long getKindId() {
        return kindId;
    }
    public void setKindId(long kindId) {
        this.kindId = kindId;
    }
    public int getTaskState() {
        return taskState;
    }
    public void setTaskState(int taskState) {
        this.taskState = taskState;
    }
    public Date getStartTime() {
        return startTime;
    }
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    public String getTaskData() {
        return taskData;
    }
    public void setTaskData(String taskData) {
        this.taskData = taskData;
    }
    
}
