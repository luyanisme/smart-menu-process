package com.example.luyan.smartmenu_process.Utils;

/**
 * Created by luyan on 15/08/2017.
 */

public class StringUtil {
    public static String contactOrders(String oreder_1, String order_2) {
        return oreder_1.substring(0, oreder_1.length() - 1) + "," + order_2.substring(1, order_2.length());
    }
}
