package com.work.unknown.absence.Admin.model;

/**
 * Created by asdasd on 14/04/2018.
 */

public class Hole
{
    String name , type ;Double  logitude;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getLogitude() {
        return logitude;
    }

    public void setLogitude(Double logitude) {
        this.logitude = logitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Hole(String name, String type, Double logitude, Double latitude) {

        this.name = name;
        this.type = type;
        this.logitude = logitude;
        this.latitude = latitude;
    }

    Double latitude;

}
