package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class FineModel implements Parcelable {
    String name;
    String reason;
    long amount;

    public FineModel(){}

    public FineModel(String name, String reason, long amount) {
        this.name = name;
        this.reason = reason;
        this.amount = amount;
    }

    protected FineModel(Parcel in) {
        name = in.readString();
        reason = in.readString();
        amount = in.readLong();
    }

    public static final Creator<FineModel> CREATOR = new Creator<FineModel>() {
        @Override
        public FineModel createFromParcel(Parcel in) {
            return new FineModel(in);
        }

        @Override
        public FineModel[] newArray(int size) {
            return new FineModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(reason);
        dest.writeLong(amount);
    }
}
