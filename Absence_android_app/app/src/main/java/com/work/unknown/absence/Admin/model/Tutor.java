package com.work.unknown.absence.Admin.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by asdasd on 14/04/2018.
 */

public class Tutor implements Parcelable {
    String name, email, password , id ;

    public String getId() {
        return id;
    }

    protected Tutor(Parcel in) {
        name = in.readString();
        email = in.readString();
        password = in.readString();
        id = in.readString() ;
    }

    public static final Creator<Tutor> CREATOR = new Creator<Tutor>() {
        @Override
        public Tutor createFromParcel(Parcel in) {
            return new Tutor(in);
        }

        @Override
        public Tutor[] newArray(int size) {
            return new Tutor[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Tutor(String name, String email, String password , String id ) {

        this.name = name;
        this.email = email;
        this.password = password;
        this.id=id ;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(id);
    }
}
