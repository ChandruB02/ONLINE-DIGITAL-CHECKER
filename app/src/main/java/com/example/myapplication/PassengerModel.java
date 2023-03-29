package com.example.myapplication;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class PassengerModel implements Parcelable {
    long Seatno;
    String Name;
    String Source;
    String Destination;
    String ID;
    String Status;


    public PassengerModel() {}

    public PassengerModel(long seatno, String name, String source, String destination, String ID, String status) {
        Seatno = seatno;
        Name = name;
        Source = source;
        Destination = destination;
        this.ID = ID;
        Status = status;
    }

    protected PassengerModel(Parcel in) {
        Seatno = in.readLong();
        Name = in.readString();
        Source = in.readString();
        Destination = in.readString();
        ID = in.readString();
        Status = in.readString();
    }

    public static final Creator<PassengerModel> CREATOR = new Creator<PassengerModel>() {
        @Override
        public PassengerModel createFromParcel(Parcel in) {
            return new PassengerModel(in);
        }

        @Override
        public PassengerModel[] newArray(int size) {
            return new PassengerModel[size];
        }
    };

    public long getSeatno() {
        return Seatno;
    }

    public void setSeatno(long seatno) {
        Seatno = seatno;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(Seatno);
        dest.writeString(Name);
        dest.writeString(Source);
        dest.writeString(Destination);
        dest.writeString(ID);
        dest.writeString(Status);
    }
}
