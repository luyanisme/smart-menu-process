package com.example.luyan.smartmenu_process.MetaData;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by luyan on 02/08/2017.
 */

public class DESKITEM implements Parcelable{
    private int deskId;
    private String deskName;//桌位名称
    private int deskCapacity;//桌位人数
    private int deskStatue;//桌位状态(0.空闲1.预订2.满座)
    private int deskCateId;//桌位分类

    public DESKITEM(Parcel in) {
        deskId = in.readInt();
        deskName = in.readString();
        deskCapacity = in.readInt();
        deskStatue = in.readInt();
        deskCateId = in.readInt();
    }

    public DESKITEM() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(deskId);
        dest.writeString(deskName);
        dest.writeInt(deskCapacity);
        dest.writeInt(deskStatue);
        dest.writeInt(deskCateId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DESKITEM> CREATOR = new Creator<DESKITEM>() {
        @Override
        public DESKITEM createFromParcel(Parcel in) {
            return new DESKITEM(in);
        }

        @Override
        public DESKITEM[] newArray(int size) {
            return new DESKITEM[size];
        }
    };

    public int getDeskId() {
        return deskId;
    }

    public void setDeskId(int deskId) {
        this.deskId = deskId;
    }

    public String getDeskName() {
        return deskName;
    }

    public void setDeskName(String deskName) {
        this.deskName = deskName;
    }

    public int getDeskCapacity() {
        return deskCapacity;
    }

    public void setDeskCapacity(int deskCapacity) {
        this.deskCapacity = deskCapacity;
    }

    public int getDeskStatue() {
        return deskStatue;
    }

    public void setDeskStatue(int deskStatue) {
        this.deskStatue = deskStatue;
    }

    public int getDeskCateId() {
        return deskCateId;
    }

    public void setDeskCateId(int deskCateId) {
        this.deskCateId = deskCateId;
    }
}
