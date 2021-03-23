package com.example.todolist;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class TasksTodo implements Parcelable {

    private String name;
    private boolean status;
    private String description;

    public TasksTodo() {
        status = false;
    }

    public TasksTodo(String name) {
        this.name = name;
        status = true;
        description = "";
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //IMPLEMENTED METHODS
    protected TasksTodo(Parcel in) {
        name = in.readString();
        status = in.readByte() != 0;
        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeByte((byte) (status ? 1 : 0));
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TasksTodo> CREATOR = new Creator<TasksTodo>() {
        @Override
        public TasksTodo createFromParcel(Parcel in) {
            return new TasksTodo(in);
        }

        @Override
        public TasksTodo[] newArray(int size) {
            return new TasksTodo[size];
        }
    };

}
