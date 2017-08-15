package com.example.luyan.smartmenu_process.Utils.GreenDaoUtils;


import com.example.luyan.smartmenu_process.Common.MyApplication;
import com.example.luyan.smartmenu_process.GreenDao.DaoMaster;
import com.example.luyan.smartmenu_process.GreenDao.DaoSession;

/**
 * Created by luyan on 2016/7/12.
 */
public class GreenDaoManager {
    private static GreenDaoManager mInstance;
    private DaoMaster mDaoMaster;

    public GreenDaoManager() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(MyApplication.getContext(), "order-db", null);
        mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
    }

    public static GreenDaoManager getInstance() {
        if (mInstance == null) {
            mInstance = new GreenDaoManager();
        }
        return mInstance;
    }

    public DaoMaster getMaster() {
        return mDaoMaster;
    }

    public DaoSession getNewSession() {
        return mDaoMaster.newSession();
    }

}