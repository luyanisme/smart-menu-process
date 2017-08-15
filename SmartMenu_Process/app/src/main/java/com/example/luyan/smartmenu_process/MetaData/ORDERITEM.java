package com.example.luyan.smartmenu_process.MetaData;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.util.ArrayList;

/**
 * Created by luyan on 31/05/2017.
 */

@Entity
public class ORDERITEM implements Parcelable {
    @Id
    private Long orderId;//订单id
    private Integer clientType = 1;//客户端类型
    private String orderKey;//消息的唯一标识
    private Long shopId;//店铺id
    private Long noticeType;//消息类型
    private Long deskId;//桌面id
    private String deskNum;//桌号
    private boolean orderIsDealed;//是否处理过
    private boolean orderIsPayed;//是否支付过
    private boolean orderIsOrdered;//是否已下单
    private boolean orderIsUsing;//是否正在使用
    private String dateTime;//时间
    private String orderContent;//简述

    private boolean isRead;//是否已经查看过

    @Generated(hash = 1559030450)
    public ORDERITEM(Long orderId, Integer clientType, String orderKey, Long shopId,
            Long noticeType, Long deskId, String deskNum, boolean orderIsDealed,
            boolean orderIsPayed, boolean orderIsOrdered, boolean orderIsUsing,
            String dateTime, String orderContent, boolean isRead) {
        this.orderId = orderId;
        this.clientType = clientType;
        this.orderKey = orderKey;
        this.shopId = shopId;
        this.noticeType = noticeType;
        this.deskId = deskId;
        this.deskNum = deskNum;
        this.orderIsDealed = orderIsDealed;
        this.orderIsPayed = orderIsPayed;
        this.orderIsOrdered = orderIsOrdered;
        this.orderIsUsing = orderIsUsing;
        this.dateTime = dateTime;
        this.orderContent = orderContent;
        this.isRead = isRead;
    }

    @Generated(hash = 907967114)
    public ORDERITEM() {
    }

    protected ORDERITEM(Parcel in) {
        orderKey = in.readString();
        deskNum = in.readString();
        orderIsDealed = in.readByte() != 0;
        orderIsPayed = in.readByte() != 0;
        orderIsOrdered = in.readByte() != 0;
        orderIsUsing = in.readByte() != 0;
        dateTime = in.readString();
        orderContent = in.readString();
        isRead = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderKey);
        dest.writeString(deskNum);
        dest.writeByte((byte) (orderIsDealed ? 1 : 0));
        dest.writeByte((byte) (orderIsPayed ? 1 : 0));
        dest.writeByte((byte) (orderIsOrdered ? 1 : 0));
        dest.writeByte((byte) (orderIsUsing ? 1 : 0));
        dest.writeString(dateTime);
        dest.writeString(orderContent);
        dest.writeByte((byte) (isRead ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ORDERITEM> CREATOR = new Creator<ORDERITEM>() {
        @Override
        public ORDERITEM createFromParcel(Parcel in) {
            return new ORDERITEM(in);
        }

        @Override
        public ORDERITEM[] newArray(int size) {
            return new ORDERITEM[size];
        }
    };

    public Long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getClientType() {
        return this.clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    public String getOrderKey() {
        return this.orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }

    public Long getShopId() {
        return this.shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getNoticeType() {
        return this.noticeType;
    }

    public void setNoticeType(Long noticeType) {
        this.noticeType = noticeType;
    }

    public Long getDeskId() {
        return this.deskId;
    }

    public void setDeskId(Long deskId) {
        this.deskId = deskId;
    }

    public String getDeskNum() {
        return this.deskNum;
    }

    public void setDeskNum(String deskNum) {
        this.deskNum = deskNum;
    }

    public boolean getOrderIsDealed() {
        return this.orderIsDealed;
    }

    public void setOrderIsDealed(boolean orderIsDealed) {
        this.orderIsDealed = orderIsDealed;
    }

    public boolean getOrderIsPayed() {
        return this.orderIsPayed;
    }

    public void setOrderIsPayed(boolean orderIsPayed) {
        this.orderIsPayed = orderIsPayed;
    }

    public boolean getOrderIsOrdered() {
        return this.orderIsOrdered;
    }

    public void setOrderIsOrdered(boolean orderIsOrdered) {
        this.orderIsOrdered = orderIsOrdered;
    }

    public boolean getOrderIsUsing() {
        return this.orderIsUsing;
    }

    public void setOrderIsUsing(boolean orderIsUsing) {
        this.orderIsUsing = orderIsUsing;
    }

    public String getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getOrderContent() {
        return this.orderContent;
    }

    public void setOrderContent(String orderContent) {
        this.orderContent = orderContent;
    }

    public boolean getIsRead() {
        return this.isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }


}
