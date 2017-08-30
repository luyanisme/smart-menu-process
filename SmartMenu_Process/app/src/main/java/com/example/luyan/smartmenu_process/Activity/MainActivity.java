package com.example.luyan.smartmenu_process.Activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.luyan.smartmenu_process.Adapter.DeskAdapter;
import com.example.luyan.smartmenu_process.Adapter.OrderAdapter;
import com.example.luyan.smartmenu_process.MetaData.CASEITEM;
import com.example.luyan.smartmenu_process.MetaData.DESKITEM;
import com.example.luyan.smartmenu_process.MetaData.NOTICEITEM;
import com.example.luyan.smartmenu_process.MetaData.ORDERITEM;
import com.example.luyan.smartmenu_process.MetaData.RESPONSE;
import com.example.luyan.smartmenu_process.MetaData.RESULT;
import com.example.luyan.smartmenu_process.Model.OrderModel;
import com.example.luyan.smartmenu_process.R;
import com.example.luyan.smartmenu_process.Service.WebSocketService;
import com.example.luyan.smartmenu_process.Utils.GreenDaoUtils.GreenDaoUtils;
import com.example.luyan.smartmenu_process.Utils.StringUtil;
import com.example.luyan.smartmenu_process.Utils.ZHHttpUtils.ZHHttpCallBack;
import com.example.luyan.smartmenu_process.Widgt.OrderDialog;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class MainActivity extends Activity implements OrderAdapter.ButtonDelegate {

    private GridView deskGridView;
    ArrayList<CASEITEM> caseitems = new ArrayList<>();
    ArrayList<ORDERITEM> orderItems = new ArrayList<>();

    private DeskAdapter deskAdapter;
    private MaterialSearchBar searchBar;
    private ListView orderList;//订单列表
    private OrderAdapter orderAdapter;
    private Intent websocketServiceIntent;
    private int orderSelectIndex = 0;
    private KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deskGridView = (GridView) findViewById(R.id.desk_grid);
        searchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
        orderList = (ListView) findViewById(R.id.order_list);

        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(getResources().getString(R.string.waiting))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        hud.show();
        getOrdersData();
        initWebSocket();
    }

    private void getOrdersData() {
        OrderModel.getInstance().getNowDayOrders(new ZHHttpCallBack<RESPONSE<List<ORDERITEM>>>() {
            @Override
            public void onSuccess(int statusCode, String rawJsonResponse, RESPONSE response) {
                if (response.getStatus() == 0) {
                    orderItems.addAll((ArrayList<ORDERITEM>)response.getData());
                    initGrid();
                    initList();
                    orderAdapter.notifyDataSetChanged();
                    hud.dismiss();
                }
            }

            @Override
            public void onFailure(int statusCode, String rawJsonResponse, RESPONSE response) {

            }
        });
    }

    private void initGrid() {
        deskAdapter = new DeskAdapter(this, orderItems);
        deskGridView.setAdapter(deskAdapter);
        deskGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                orderSelectIndex = i;
                if (!orderItems.get(i).getIsRead()) {
                    orderItems.get(i).setIsRead(true);
                    orderItems.get(i).setClientType(2);
                    WebSocketService.sendMsg(new Gson().toJson(orderItems.get(orderSelectIndex)));
                }
                caseitems.clear();
                CASEITEM[] items = new Gson().fromJson(orderItems.get(i).getOrderContent(), CASEITEM[].class);
                for (int j = 0; j < items.length; j++) {
                    caseitems.add(items[j]);
                }
                orderAdapter.notifyDataSetChanged();
                deskAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initList() {
        orderAdapter = new OrderAdapter(this, caseitems);
        orderAdapter.setDelegate(this);
        orderList.setAdapter(orderAdapter);
        if (orderItems.size() == 0) {
            return;
        }
        caseitems.clear();
        CASEITEM[] items = new Gson().fromJson(orderItems.get(0).getOrderContent(), CASEITEM[].class);
        for (int j = 0; j < items.length; j++) {
            caseitems.add(items[j]);
        }
        orderAdapter.notifyDataSetChanged();
        if (!orderItems.get(0).getIsRead()) {
            orderItems.get(0).setIsRead(true);
            deskAdapter.notifyDataSetChanged();
        }
    }

    private void initWebSocket() {
        websocketServiceIntent = new Intent(this, WebSocketService.class);
        startService(websocketServiceIntent);
        WebSocketService.webSocketConnect();
        WebSocketService.handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    RESULT result = new Gson().fromJson(msg.getData().get("notice").toString(), RESULT.class);
                    switch (result.getNoticeType()) {
                        /*消息*/
                        case 0:
                            NOTICEITEM noticeitem = new Gson().fromJson(msg.getData().get("notice").toString(), NOTICEITEM.class);
                            break;

                        /*订单*/
                        case 1:
                            ORDERITEM clientOrderitem = new Gson().fromJson(msg.getData().get("notice").toString(), ORDERITEM.class);
                            clientOrderitem.setIsRead(false);
                            for (int i = 0; i < orderItems.size(); i++) {
                                if (clientOrderitem.getDeskId().equals(orderItems.get(i).getDeskId())) {
                                    orderItems.get(i).setOrderContent(StringUtil.contactOrders(orderItems.get(i).getOrderContent(), clientOrderitem.getOrderContent())
                                    );
                                    orderItems.get(i).setIsRead(false);
                                    deskAdapter.notifyDataSetChanged();
                                    return;
                                }
                            }
                            orderItems.add(clientOrderitem);
                            deskAdapter.notifyDataSetChanged();
                            break;

                        /*订单*/
                        case 2:
                            ORDERITEM severOrderitem = new Gson().fromJson(msg.getData().get("notice").toString(), ORDERITEM.class);
                            severOrderitem.setIsRead(false);
                            for (int i = 0; i < orderItems.size(); i++) {
                                if (severOrderitem.getDeskId().equals(orderItems.get(i).getDeskId())) {
                                    orderItems.get(i).setOrderContent(StringUtil.contactOrders(orderItems.get(i).getOrderContent(), severOrderitem.getOrderContent())
                                    );
                                    orderItems.get(i).setIsRead(false);
                                    deskAdapter.notifyDataSetChanged();
                                    return;
                                }
                            }
                            orderItems.add(severOrderitem);
                            deskAdapter.notifyDataSetChanged();
                            break;
                    }

                }
            }
        };
    }

    @Override
    public void tapOperateButton(int index, int progress) {
        caseitems.get(index).setCaseProgress(progress);
        orderAdapter.notifyDataSetChanged();
        orderItems.get(orderSelectIndex).setOrderContent(new Gson().toJson(caseitems));
        orderItems.get(orderSelectIndex).setNoticeType((long) 1);
        orderItems.get(orderSelectIndex).setClientType(2);
        WebSocketService.sendMsg(new Gson().toJson(orderItems.get(orderSelectIndex)));
    }
}
