package com.work.unknown.absence.Models;

/**
 * Created by unknown on 4/13/2018.
 */

public class sesstion {
    public sesstion(){}
    String sesstion_name;
    String Sesstion_StartTime;
    String Sesstion_endTime;
    String Sesstion_Allowed_time;
    String Place;
    String sesstion_id;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    String Date;
     Double lat,lng;
    public sesstion(String sesstion_name, String sesstion_StartTime, String sesstion_endTime, String sesstion_Allowed_time, String place, String sesstion_id, Double lat, Double lng,String Date) {
        this.sesstion_name = sesstion_name;
        Sesstion_StartTime = sesstion_StartTime;
        Sesstion_endTime = sesstion_endTime;
        Sesstion_Allowed_time = sesstion_Allowed_time;
        Place = place;
        this.sesstion_id = sesstion_id;
        this.lat = lat;
        this.lng = lng;
        this.Date=Date;
    }

    public String getSesstion_name() {
        return sesstion_name;
    }

    public void setSesstion_name(String sesstion_name) {
        this.sesstion_name = sesstion_name;
    }

    public String getSesstion_StartTime() {
        return Sesstion_StartTime;
    }

    public void setSesstion_StartTime(String sesstion_StartTime) {
        Sesstion_StartTime = sesstion_StartTime;
    }

    public String getSesstion_endTime() {
        return Sesstion_endTime;
    }

    public void setSesstion_endTime(String sesstion_endTime) {
        Sesstion_endTime = sesstion_endTime;
    }

    public String getSesstion_Allowed_time() {
        return Sesstion_Allowed_time;
    }

    public void setSesstion_Allowed_time(String sesstion_Allowed_time) {
        Sesstion_Allowed_time = sesstion_Allowed_time;
    }

    public String getPlace() {
        return Place;
    }

    public void setPlace(String place) {
        Place = place;
    }

    public String getSesstion_id() {
        return sesstion_id;
    }

    public void setSesstion_id(String sesstion_id) {
        this.sesstion_id = sesstion_id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
