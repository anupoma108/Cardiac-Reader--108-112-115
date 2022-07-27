package com.example.cardiacreader.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Task implements Serializable {
/*
*task id is primary key, and the other parameter inside it. After that i have create getter and setter bellow
*/
    @PrimaryKey(autoGenerate = true)
    int taskId;
    @ColumnInfo(name = "date")
    String date;
    @ColumnInfo(name = "time")
    String time;
    @ColumnInfo(name = "systolic_pressure")
    String systolic_pressure;
    @ColumnInfo(name = "diastolic_pressure")
    String diastolic_pressure;
    @ColumnInfo(name = "heart_rate")
    String heart_rate;
    @ColumnInfo(name = "comment")
    String comment;

    public Task() {

    }
    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSystolic_pressure() {
        return systolic_pressure;
    }

    public void setSystolic_pressure(String systolic_pressure) {
        this.systolic_pressure = systolic_pressure;
    }

    public String getDiastolic_pressure() {
        return diastolic_pressure;
    }

    public void setDiastolic_pressure(String diastolic_pressure) {
        this.diastolic_pressure = diastolic_pressure;
    }

    public String getHeart_rate() {
        return heart_rate;
    }

    public void setHeart_rate(String heart_rate) {
        this.heart_rate = heart_rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
