package com.example.apiapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class model {

    @SerializedName("DoorID")
    private String DoorID;

    @SerializedName("_LockID")
    private String _LockID;

    @SerializedName("_LockNumber")
    private String _LockNumber;

    @SerializedName("_LockType")
    private String _LockType;

    public model(String DoorID, String _LockID, String _LockNumber, String _LockType) {
        this.DoorID = DoorID;
        this._LockID = _LockID;
        this._LockNumber = _LockNumber;
        this._LockType = _LockType;
    }

    public String getDoorID() {
        return DoorID;
    }

    public String get_LockID() {
        return _LockID;
    }

    public String get_LockNumber() {
        return _LockNumber;
    }

    public String get_LockType() {
        return _LockType;
    }

    @Override
    public String toString() {
        return "model{" +
                "DoorID='" + DoorID + '\'' +
                ", _LockID='" + _LockID + '\'' +
                ", _DoorName='" + _LockNumber + '\'' +
                ", _LockType='" + _LockType + '\'' +
                '}';
    }
}
