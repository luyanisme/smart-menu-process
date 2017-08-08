package com.example.luyan.smartmenu_process;

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
import com.example.luyan.smartmenu_process.MetaData.RESULT;
import com.example.luyan.smartmenu_process.Service.WebSocketService;
import com.example.luyan.smartmenu_process.Utils.GreenDaoUtils.GreenDaoUtils;
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

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.ACTION_TIME_TICK.equals(intent.getAction())) {
                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
                String time = df.format(new Date());
                if (time.equals("06:00:00")) {
                    GreenDaoUtils.clearDB();
                }
            } else if (intent.ACTION_TIME_CHANGED.equals(intent.getAction())) {

            }
        }
    };

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

        readDataFromDB();
        initWebSocket();
        initGrid();
        initList();

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        filter.addAction(Intent.ACTION_TIME_CHANGED);
        registerReceiver(broadcastReceiver, filter);
    }

    private void readDataFromDB() {
        List<Object> orders = GreenDaoUtils.queryObjects(ORDERITEM.class, null);
        for (int i = 0; i < orders.size(); i++) {
            orderItems.add((ORDERITEM) orders.get(i));
        }
    }

    private void initGrid() {
        deskAdapter = new DeskAdapter(this, orderItems);
        deskGridView.setAdapter(deskAdapter);
        deskGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                orderSelectIndex = i;
                orderItems.get(i).setIsRead(true);
                GreenDaoUtils.updateObject(orderItems.get(i));
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
                            ORDERITEM orderitem = new Gson().fromJson(msg.getData().get("notice").toString(), ORDERITEM.class);
                            orderitem.setIsRead(false);
                            GreenDaoUtils.insertObject(orderitem);
                            orderItems.add(orderitem);
                            deskAdapter.notifyDataSetChanged();
                            break;
                    }

                }
            }
        };
    }

    @Override
    public void tapButton(int index) {
        caseitems.get(index).setCaseProgress(3);
        orderAdapter.notifyDataSetChanged();
        orderItems.get(orderSelectIndex).setOrderContent(new Gson().toJson(caseitems));
        GreenDaoUtils.updateObject(orderItems.get(orderSelectIndex));
        WebSocketService.sendMsg(new Gson().toJson(orderItems.get(orderSelectIndex)));
    }
}
