package com.example.crud;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
public class goal {
    private Integer Goalid;
    private String Title;
    private String Details;
    private Integer ETA;
    private Date createDate;
    private Date updateDate;

    public goal() {
        super();
    }

    public goal(Integer goalid, String title, String details, Integer ETA, Date createDate, Date updateDate) {
        Goalid = goalid;
        Title = title;
        Details = details;
        this.ETA = ETA;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    public Integer getGoalid() {
        return Goalid;
    }

    public String getTitle() {
        return Title;
    }

    public String getDetails() {
        return Details;
    }

    public Integer getETA() {
        return ETA;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setGoalid(Integer goalid) {
        Goalid = goalid;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public void setETA(Integer ETA) {
        this.ETA = ETA;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "Goals{" +
                "GoalId=" + Goalid +
                ", title='" + Title + '\'' +
                ", details='" + Details + '\'' +
                ", eta='" + ETA + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }

}