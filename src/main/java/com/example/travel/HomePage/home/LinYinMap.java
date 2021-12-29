package com.example.travel.HomePage.home;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.travel.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

public class LinYinMap extends Activity {
    protected LatLng target=new LatLng(30.246914,120.107833);
    private BottomSheetBehavior mBottomSheetBehavior_food;
    private BottomSheetBehavior mBottomSheetBehavior_scenery;
    private BottomSheetBehavior mBottomSheetBehavior_hotel;
    private BroadcastReceiver receiver;
    private ImageButton mbutton = null;
    private MapView mMapView = null;
    View mBehavior_food;
    View mBehavior_scenery;
    View mBehavior_hotel;
    private Button btn_food;
    private Button btn_close1;
    private Button btn_scenery;
    private Button btn_close2;
    private Button btn_hotel;
    private Button btn_close3;
    BaiduMap mBaiduMap;
    MapStatusUpdate mapStatusUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linyinmap);
        mMapView = (MapView) findViewById(R.id.map_lingyin);
        mBaiduMap = mMapView.getMap();
        //普通地图 ,mBaiduMap是地图控制器对象
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        registerSDKCheckReceiver();
        // 3.  设置地图中心点为target
        mapStatusUpdate= MapStatusUpdateFactory.newLatLng(target);
        mBaiduMap.setMapStatus(mapStatusUpdate);
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.map_point);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(target)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
        //4.   设置地图缩放为15
        mapStatusUpdate= MapStatusUpdateFactory.zoomTo(18);
        mBaiduMap.setMapStatus(mapStatusUpdate);

        init();
    }

    private void init(){
        mbutton = findViewById(R.id.lingyin_map_return);
        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinYinMap.this.finish();
            }
        });

        btn_food = (Button) findViewById(R.id.btn_LingYin_food);
        btn_scenery = findViewById(R.id.btn_LingYin_scenery);
        btn_hotel = findViewById(R.id.btn_LingYin_hotel);
        btn_close3 = (Button) findViewById(R.id.LingYin__map_close3) ;
        btn_close2 = (Button) findViewById(R.id.LingYin__map_close2) ;
        btn_close1 = (Button) findViewById(R.id.LingYin_map_close1) ;
        mBehavior_food = findViewById(R.id.LingYin_bottomSheet1);
        mBehavior_scenery = findViewById(R.id.LingYin_bottomSheet2);
        mBehavior_hotel = findViewById(R.id.LingYin_bottomSheet3);

        RecyclerView recyclerView_food = (RecyclerView) mBehavior_food.findViewById(R.id.LingYin_map_recyclerview1);
        mBottomSheetBehavior_food = BottomSheetBehavior.from(mBehavior_food);

        RecyclerView recyclerView_scenery = (RecyclerView) mBehavior_scenery.findViewById(R.id.LingYin__map_recyclerview2);
        mBottomSheetBehavior_scenery = BottomSheetBehavior.from(mBehavior_scenery);

        RecyclerView recyclerView_hotel = (RecyclerView) mBehavior_hotel.findViewById(R.id.LingYin__map_recyclerview3);
        mBottomSheetBehavior_hotel = BottomSheetBehavior.from(mBehavior_hotel);

        mBottomSheetBehavior_food.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        recyclerView_food.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_food.setHasFixedSize(true);//不用重新计算大小
        recyclerView_food.setAdapter(new ItemAdapter(createFoodItems(), new ItemAdapter.OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                if (pos == 0){
                    setEatingLat(30.263562,120.065917);
                }else if (pos == 1){
                    setEatingLat(30.253782,120.057864);
                }else if (pos==2){
                    setEatingLat(30.245311,120.112476);
                }
            }
        }));

        btn_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBottomSheetBehavior_food.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    mBottomSheetBehavior_food.setState(BottomSheetBehavior.STATE_EXPANDED);
                    Handler mHandler = new Handler();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            CreateFoodMark();
                        }
                    });
                } else {
                    mBottomSheetBehavior_food.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    Handler mHandler = new Handler();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mBaiduMap.clear();
                        }
                    });
                }
            }
        });

        btn_close1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetBehavior_food.setState(BottomSheetBehavior.STATE_COLLAPSED);
                Handler mHandler = new Handler();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mBaiduMap.clear();
                        recovery(30.246914,120.107833);
                    }
                });
            }
        });

        mBottomSheetBehavior_scenery.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        recyclerView_scenery.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_scenery.setHasFixedSize(true);
        recyclerView_scenery.setAdapter(new ItemAdapter(createSceneryItems(), new ItemAdapter.OnItemClickListener() {

            @Override
            public void onClick(int pos) {
                if (pos == 0){
                    setSceneryLat(30.246157,120.107416);
                }else if (pos == 1){
                    setSceneryLat(30.228932,120.12792);
                }else if (pos==2){
                    setSceneryLat(30.27289,120.071528);
                }else if (pos==3){
                    setSceneryLat(30.236839,120.155358);
                }
            }
        }));


        btn_scenery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBottomSheetBehavior_scenery.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    mBottomSheetBehavior_scenery.setState(BottomSheetBehavior.STATE_EXPANDED);
                    Handler mHandler = new Handler();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            CreateSceneryMark();
                        }
                    });
                } else {
                    mBottomSheetBehavior_scenery.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    Handler mHandler = new Handler();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mBaiduMap.clear();
                        }
                    });
                }
            }
        });

        btn_close2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetBehavior_scenery.setState(BottomSheetBehavior.STATE_COLLAPSED);
                Handler mHandler = new Handler();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mBaiduMap.clear();
                    }
                });
                recovery(30.246914,120.107833);
            }
        });

        mBottomSheetBehavior_hotel.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        recyclerView_hotel.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_hotel.setHasFixedSize(true);
        recyclerView_hotel.setAdapter(new ItemAdapter(createHotelItems(), new ItemAdapter.OnItemClickListener() {

            /*
            LatLng point1 = new LatLng(30.257,120.060779);
        LatLng point2 = new LatLng(30.263252,120.067496);
        LatLng point3 = new LatLng(30.26186,120.091041);
        LatLng point4 = new LatLng(30.258985,120.069013);
             */
            @Override
            public void onClick(int pos) {
                if (pos == 0){
                    setLivingLat(30.257,120.060779);
                }else if (pos == 1){
                    setLivingLat(30.263252,120.067496);
                }else if (pos==2){
                    setLivingLat(30.26186,120.091041);
                }else if (pos==3){
                    setLivingLat(30.258985,120.069013);
                }
            }
        }));


        btn_hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBottomSheetBehavior_hotel.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    mBottomSheetBehavior_hotel.setState(BottomSheetBehavior.STATE_EXPANDED);
                    Handler mHandler = new Handler();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            CreateHotelMark();
                        }
                    });
                } else {
                    mBottomSheetBehavior_hotel.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    Handler mHandler = new Handler();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mBaiduMap.clear();
                        }
                    });
                }
            }
        });

        btn_close3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetBehavior_hotel.setState(BottomSheetBehavior.STATE_COLLAPSED);
                Handler mHandler = new Handler();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mBaiduMap.clear();
                    }
                });
                recovery(30.246914,120.107833);
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        unregisterReceiver(receiver);
    }


    private void registerSDKCheckReceiver(){
        receiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action=intent.getAction();
                if (SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR.equals(action)){
                    Toast.makeText(getApplicationContext(),"网络错误",Toast.LENGTH_SHORT).show();
                }else if (SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR.equals(action)){
                    Toast.makeText(getApplicationContext(),"KEY验证失败",Toast.LENGTH_SHORT).show();
                }
            }
        };
        IntentFilter filter=new IntentFilter();
        //监听网络错误
        filter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        //监听百度地图SDK的key是否正确
        filter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        registerReceiver(receiver,filter);
    }

    public List<Item> createFoodItems() {
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item(R.drawable.xixibieyuan, "西湖别院","西溪  私房菜"));
        items.add(new Item(R.drawable.nongtaoli, "弄堂里","西溪  浙菜"));
        items.add(new Item(R.drawable.zhizhu, "知竹","灵隐/白乐桥  素食"));
        return items;
    }

    public List<Item> createSceneryItems() {
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item(R.drawable.feilaifeng, "灵隐飞来峰景区","龙井/虎跑"));
        items.add(new Item(R.drawable.xihutitle, "西湖风景名胜区","龙井/虎跑"));
        items.add(new Item(R.drawable.xixititle, "西溪国家湿地公园","灵隐寺/白乐桥"));
        items.add(new Item(R.drawable.leifengta, "雷峰塔景区","南山路/柳浪闻鸳"));
        return items;
    }

    public List<Item> createHotelItems() {
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item(R.drawable.huatang, "杭州西溪花间堂","豪华型"));
        items.add(new Item(R.drawable.shilifenfei, "十里芳菲度假村落","豪华型"));
        items.add(new Item(R.drawable.yaduo, "亚朵酒店","高档型"));
        items.add(new Item(R.drawable.qibo, "栖泊精选酒店","舒适性"));
        return items;
    }

    private void CreateHotelMark(){
        List<OverlayOptions> options = new ArrayList<OverlayOptions>();
        LatLng point1 = new LatLng(30.257,120.060779);
        LatLng point2 = new LatLng(30.263252,120.067496);
        LatLng point3 = new LatLng(30.26186,120.091041);
        LatLng point4 = new LatLng(30.258985,120.069013);

        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.hotel);

        OverlayOptions option1 =  new MarkerOptions()
                .position(point1)
                .icon(bitmap);
        OverlayOptions option2 =  new MarkerOptions()
                .position(point2)
                .icon(bitmap);
        OverlayOptions option3 =  new MarkerOptions()
                .position(point3)
                .icon(bitmap);
        OverlayOptions option4 =  new MarkerOptions()
                .position(point4)
                .icon(bitmap);
//将OverlayOptions添加到list
        options.add(option1);
        options.add(option2);
        options.add(option3);
        options.add(option4);
//在地图上批量添加
        mBaiduMap.addOverlays(options);
    }

    private void CreateFoodMark(){
        List<OverlayOptions> options = new ArrayList<OverlayOptions>();
        LatLng point1 = new LatLng(30.263562,120.065917);
        LatLng point2 = new LatLng(30.253782,120.057864);
        LatLng point3 = new LatLng(30.271329,120.097022);
        LatLng point4 = new LatLng(30.258186,120.060658);

        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.eat);

        OverlayOptions option1 =  new MarkerOptions()
                .position(point1)
                .icon(bitmap);
        OverlayOptions option2 =  new MarkerOptions()
                .position(point2)
                .icon(bitmap);
        OverlayOptions option3 =  new MarkerOptions()
                .position(point3)
                .icon(bitmap);
        OverlayOptions option4 =  new MarkerOptions()
                .position(point4)
                .icon(bitmap);
//将OverlayOptions添加到list
        options.add(option1);
        options.add(option2);
        options.add(option3);
        options.add(option4);
//在地图上批量添加
        mBaiduMap.addOverlays(options);
    }

    private void CreateSceneryMark(){
        List<OverlayOptions> options = new ArrayList<OverlayOptions>();
        LatLng point1 = new LatLng(30.246157,120.107416);
        LatLng point2 = new LatLng(30.228932,120.12792);
        LatLng point3 = new LatLng(30.246914,120.107833);
        LatLng point4 = new LatLng(30.236839,120.155358);

        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.scenecy);

        OverlayOptions option1 =  new MarkerOptions()
                .position(point1)
                .icon(bitmap);
        OverlayOptions option2 =  new MarkerOptions()
                .position(point2)
                .icon(bitmap);
        OverlayOptions option3 =  new MarkerOptions()
                .position(point3)
                .icon(bitmap);
        OverlayOptions option4 =  new MarkerOptions()
                .position(point4)
                .icon(bitmap);
//将OverlayOptions添加到list
        options.add(option1);
        options.add(option2);
        options.add(option3);
        options.add(option4);
//在地图上批量添加
        mBaiduMap.addOverlays(options);
        mapStatusUpdate= MapStatusUpdateFactory.zoomTo(13);
        mBaiduMap.setMapStatus(mapStatusUpdate);
    }

    private void setEatingLat(final double x, final double y){
        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mBaiduMap.clear();
                LatLng point = new LatLng(x, y);
                //构建Marker图标
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.eat);
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .position(point)
                        .icon(bitmap);
                //在地图上添加Marker，并显示
                mBaiduMap.addOverlay(option);
                mapStatusUpdate= MapStatusUpdateFactory.newLatLng(new LatLng(x,y));
                mBaiduMap.setMapStatus(mapStatusUpdate);
                //4.   设置地图缩放为15
                mapStatusUpdate= MapStatusUpdateFactory.zoomTo(20);
                mBaiduMap.setMapStatus(mapStatusUpdate);
            }
        });
    }

    private void setLivingLat(final double x, final double y){
        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mBaiduMap.clear();
                LatLng point = new LatLng(x, y);
                //构建Marker图标
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.hotel);
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .position(point)
                        .icon(bitmap);
                //在地图上添加Marker，并显示
                mBaiduMap.addOverlay(option);
                mapStatusUpdate= MapStatusUpdateFactory.newLatLng(new LatLng(x,y));
                mBaiduMap.setMapStatus(mapStatusUpdate);
                //4.   设置地图缩放为15
                mapStatusUpdate= MapStatusUpdateFactory.zoomTo(20);
                mBaiduMap.setMapStatus(mapStatusUpdate);
            }
        });
    }

    private void setSceneryLat(final double x, final double y){
        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mBaiduMap.clear();
                LatLng point = new LatLng(x, y);
                //构建Marker图标
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.scenecy);
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .position(point)
                        .icon(bitmap);
                //在地图上添加Marker，并显示
                mBaiduMap.addOverlay(option);
                mapStatusUpdate= MapStatusUpdateFactory.newLatLng(new LatLng(x,y));
                mBaiduMap.setMapStatus(mapStatusUpdate);
                //4.   设置地图缩放为15
                mapStatusUpdate= MapStatusUpdateFactory.zoomTo(15);
                mBaiduMap.setMapStatus(mapStatusUpdate);
            }
        });
    }

    private void recovery(final double x, final double y){
        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mapStatusUpdate= MapStatusUpdateFactory.newLatLng(new LatLng(x,y));
                mBaiduMap.setMapStatus(mapStatusUpdate);
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.map_point);
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .position(target)
                        .icon(bitmap);
                //在地图上添加Marker，并显示
                mBaiduMap.addOverlay(option);
                //4.   设置地图缩放为15
                mapStatusUpdate= MapStatusUpdateFactory.zoomTo(18);
                mBaiduMap.setMapStatus(mapStatusUpdate);
            }
        });
    }
}
