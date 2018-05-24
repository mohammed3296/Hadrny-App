package com.work.unknown.absence.Models;

/**
 * Created by Mohammed El_amary on 16/04/2018.
 */

public class StudentModel {
    private String id;
    private String name;
    private String deviceId;
    private String nationalId;
    private String SeatingId;
    private String level;
    private String department;

    public StudentModel(String id, String name, String deviceId, String nationalId, String seatingId, String level, String department) {
        this.id = id;
        this.name = name;
        this.deviceId = deviceId;
        this.nationalId = nationalId;
        SeatingId = seatingId;
        this.level = level;
        this.department = department;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getSeatingId() {
        return SeatingId;
    }

    public void setSeatingId(String seatingId) {
        SeatingId = seatingId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
