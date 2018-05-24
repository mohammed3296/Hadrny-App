package com.work.unknown.absence.Models;

/**
 * Created by Mohammed El_amary on 14/04/2018.
 */

public class SessionModel {
    private String id;
    private String Name;
    private String Hall_name;
    private String Registeration_time;
    private String numberOfStudents;
    private String Start_time;
    private String Date;

    public SessionModel(String id, String name, String hall_name, String registeration_time, String numberOfStudents, String start_time, String date) {
        this.id = id;
        Name = name;
        Hall_name = hall_name;
        Registeration_time = registeration_time;
        this.numberOfStudents = numberOfStudents;
        Start_time = start_time;
        Date = date;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public String getHall_name() {
        return Hall_name;
    }

    public String getRegisteration_time() {
        return Registeration_time;
    }

    public String getNumberOfStudents() {
        return numberOfStudents;
    }

    public String getStart_time() {
        return Start_time;
    }

    public String getDate() {
        return Date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setHall_name(String hall_name) {
        Hall_name = hall_name;
    }

    public void setRegisteration_time(String registeration_time) {
        Registeration_time = registeration_time;
    }

    public void setNumberOfStudents(String numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public void setStart_time(String start_time) {
        Start_time = start_time;
    }

    public void setDate(String date) {
        Date = date;
    }

    @Override
    public String toString() {
        return "SessionModel{" +
                "id='" + id + '\'' +
                ", Name='" + Name + '\'' +
                ", Hall_name='" + Hall_name + '\'' +
                ", Registeration_time='" + Registeration_time + '\'' +
                ", numberOfStudents='" + numberOfStudents + '\'' +
                ", Start_time='" + Start_time + '\'' +
                ", Date='" + Date + '\'' +
                '}';
    }
}
