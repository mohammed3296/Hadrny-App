package com.work.unknown.absence.Admin.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by asdasd on 17/04/2018.
 */

public class Student implements Parcelable
{
    String id;
    String name;
    String address;
    String deptartement;
    String level;
    String deviceId;
    String nationalId;
    String seatingNumber ;

    protected Student(Parcel in) {
        id = in.readString();
        name = in.readString();
        address = in.readString();
        deptartement = in.readString();
        level = in.readString();
        deviceId = in.readString();
        nationalId = in.readString();
        seatingNumber = in.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDeptartement() {
        return deptartement;
    }

    public void setDeptartement(String deptartement) {
        this.deptartement = deptartement;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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

    public String getSeatingNumber() {
        return seatingNumber;
    }

    public void setSeatingNumber(String seatingNumber) {
        this.seatingNumber = seatingNumber;
    }

    public Student(String id, String name, String address, String deptartement,
                   String level, String deviceId, String nationalId, String seatingNumber) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.deptartement = deptartement;
        this.level = level;
        this.deviceId = deviceId;
        this.nationalId = nationalId;
        this.seatingNumber = seatingNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(deptartement);
        dest.writeString(level);
        dest.writeString(deviceId);
        dest.writeString(nationalId);
        dest.writeString(seatingNumber);
    }
}
