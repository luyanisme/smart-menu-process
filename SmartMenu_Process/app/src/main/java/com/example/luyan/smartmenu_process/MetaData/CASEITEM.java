package com.example.luyan.smartmenu_process.MetaData;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by luyan on 09/06/2017.
 */

public class CASEITEM implements Parcelable {
    private long caseId;
    private float casePrice;
    private String caseName;//商品名称
    private int caseHot;//商品热度
    private int caseTypeId;//商品类型
    private String caseImagePath;//商品图片
    private ArrayList<CASESTANDARDITEM> caseStandardVals;//规格
    private ArrayList<CASEPROPERTYITEM> casePropertyVals;//属性
    private int caseProgress;//制作进度
    private boolean caseScaling;//是否上架
    private Long caseScaledNums;//已售
    private String updateTime;//更新时间

    private Integer cateIndex;//种类序号
    private int orderNum;//已订数量
    private CASESTANDARDITEM casestandarditem;//已选规格
    private CASEPROPERTYITEM casepropertyitem;//已选属性值
    private String standardDesc;//规格描述
    private ArrayList<CASEITEM> orderCases = new ArrayList<>();//已选不同属性商品

    public CASEITEM() {

    }


    protected CASEITEM(Parcel in) {
        caseId = in.readLong();
        casePrice = in.readFloat();
        caseName = in.readString();
        caseHot = in.readInt();
        caseTypeId = in.readInt();
        caseImagePath = in.readString();
        caseStandardVals = in.createTypedArrayList(CASESTANDARDITEM.CREATOR);
        casePropertyVals = in.createTypedArrayList(CASEPROPERTYITEM.CREATOR);
        caseProgress = in.readInt();
        caseScaling = in.readByte() != 0;
        updateTime = in.readString();
        orderNum = in.readInt();
        casestandarditem = in.readParcelable(CASESTANDARDITEM.class.getClassLoader());
        casepropertyitem = in.readParcelable(CASEPROPERTYITEM.class.getClassLoader());
        standardDesc = in.readString();
        orderCases = in.createTypedArrayList(CASEITEM.CREATOR);
    }

    public static final Creator<CASEITEM> CREATOR = new Creator<CASEITEM>() {
        @Override
        public CASEITEM createFromParcel(Parcel in) {
            return new CASEITEM(in);
        }

        @Override
        public CASEITEM[] newArray(int size) {
            return new CASEITEM[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(caseId);
        parcel.writeFloat(casePrice);
        parcel.writeString(caseName);
        parcel.writeInt(caseHot);
        parcel.writeInt(caseTypeId);
        parcel.writeString(caseImagePath);
        parcel.writeTypedList(caseStandardVals);
        parcel.writeTypedList(casePropertyVals);
        parcel.writeInt(caseProgress);
        parcel.writeByte((byte) (caseScaling ? 1 : 0));
        parcel.writeString(updateTime);
        parcel.writeInt(orderNum);
        parcel.writeParcelable(casestandarditem, i);
        parcel.writeParcelable(casepropertyitem, i);
        parcel.writeString(standardDesc);
        parcel.writeTypedList(orderCases);
    }

    public long getCaseId() {
        return caseId;
    }

    public void setCaseId(long caseId) {
        this.caseId = caseId;
    }

    public float getCasePrice() {
        return casePrice;
    }

    public void setCasePrice(float casePrice) {
        this.casePrice = casePrice;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public int getCaseHot() {
        return caseHot;
    }

    public void setCaseHot(int caseHot) {
        this.caseHot = caseHot;
    }

    public int getCaseTypeId() {
        return caseTypeId;
    }

    public void setCaseTypeId(int caseTypeId) {
        this.caseTypeId = caseTypeId;
    }

    public String getCaseImagePath() {
        return caseImagePath;
    }

    public void setCaseImagePath(String caseImagePath) {
        this.caseImagePath = caseImagePath;
    }

    public ArrayList<CASESTANDARDITEM> getCaseStandardVals() {
        return caseStandardVals;
    }

    public void setCaseStandardVals(ArrayList<CASESTANDARDITEM> caseStandardVals) {
        this.caseStandardVals = caseStandardVals;
    }

    public ArrayList<CASEPROPERTYITEM> getCasePropertyVals() {
        return casePropertyVals;
    }

    public void setCasePropertyVals(ArrayList<CASEPROPERTYITEM> casePropertyVals) {
        this.casePropertyVals = casePropertyVals;
    }

    public int getCaseProgress() {
        return caseProgress;
    }

    public void setCaseProgress(int caseProgress) {
        this.caseProgress = caseProgress;
    }

    public boolean isCaseScaling() {
        return caseScaling;
    }

    public void setCaseScaling(boolean caseScaling) {
        this.caseScaling = caseScaling;
    }

    public Long getCaseScaledNums() {
        return caseScaledNums;
    }

    public void setCaseScaledNums(Long caseScaledNums) {
        this.caseScaledNums = caseScaledNums;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCateIndex() {
        return cateIndex;
    }

    public void setCateIndex(Integer cateIndex) {
        this.cateIndex = cateIndex;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public CASESTANDARDITEM getCasestandarditem() {
        return casestandarditem;
    }

    public void setCasestandarditem(CASESTANDARDITEM casestandarditem) {
        this.casestandarditem = casestandarditem;
    }

    public CASEPROPERTYITEM getCasepropertyitem() {
        return casepropertyitem;
    }

    public void setCasepropertyitem(CASEPROPERTYITEM casepropertyitem) {
        this.casepropertyitem = casepropertyitem;
    }

    public String getStandardDesc() {
        return standardDesc;
    }

    public void setStandardDesc(String standardDesc) {
        this.standardDesc = standardDesc;
    }

    public ArrayList<CASEITEM> getOrderCases() {
        return orderCases;
    }

    public void setOrderCases(ArrayList<CASEITEM> orderCases) {
        this.orderCases = orderCases;
    }
}
