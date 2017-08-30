package com.example.luyan.smartmenu_process.Model;

import android.content.Context;

import com.example.luyan.smartmenu_process.Common.ServerConfig;
import com.example.luyan.smartmenu_process.Utils.ZHHttpUtils.ZHHttpCallBack;
import com.example.luyan.smartmenu_process.Utils.ZHHttpUtils.ZHHttpHelper;
import com.google.gson.Gson;

import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by luyan on 20/06/2017.
 */

public class OrderModel {
    /* 持有私有静态实例，防止被引用，此处赋值为null，目的是实现延迟加载 */
    private static OrderModel instance = null;

    /* 私有构造方法，防止被实例化 */
    private OrderModel() {
    }

    /*加上synchronized，但是每次调用实例时都会加载**/
    public static OrderModel getInstance() {
        synchronized (OrderModel.class) {
            if (instance == null) {
                instance = new OrderModel();
            }
        }
        return instance;
    }

    public void getNowDayOrders(ZHHttpCallBack httpCallBack){
        ZHHttpHelper.getInstance().get(ServerConfig.HTTP + "getNowdayOrders?shopId="+UserModel.getInstance().getUserinfo().getShopId(), null, httpCallBack);
    }

}
