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
import androidx.coordinatorlayout.widget.CoordinatorLayout;
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

public class XiXiMap extends Activity {
    private BottomSheetBehavior mBottomSheetBehavior_food;
    private BottomSheetBehavior mBottomSheetBehavior_scenery;
    private BottomSheetBehavior mBottomSheetBehavior_hotel;
    private BroadcastReceiver receiver;
    private ImageButton mbutton = null;
    private MapView mMapView = null;
    View mBehavior_food;
    View mBehavior_scenery;
    View mBehavior_hotel;
    protected LatLng target=new LatLng(30.27289,120.071528);
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
        setContentView(R.layout.xiximap);
        mMapView = (MapView) findViewById(R.id.map_xixi);
        mBaiduMap = mMapView.getMap();
        //???????????? ,mBaiduMap????????????????????????
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        registerSDKCheckReceiver();
        // 3.  ????????????????????????target
        mapStatusUpdate= MapStatusUpdateFactory.newLatLng(target);
        mBaiduMap.setMapStatus(mapStatusUpdate);
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.map_point);
        //??????MarkerOption???????????????????????????Marker
        OverlayOptions option = new MarkerOptions()
                .position(target)
                .icon(bitmap);
        //??????????????????Marker????????????
        mBaiduMap.addOverlay(option);
        //4.   ?????????????????????15
        mapStatusUpdate= MapStatusUpdateFactory.zoomTo(15);
        mBaiduMap.setMapStatus(mapStatusUpdate);

        init();
    }

    private void init(){
        mbutton = findViewById(R.id.xixi_map_return);
        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XiXiMap.this.finish();
            }
        });

        btn_food = (Button) findViewById(R.id.btn_XiXi_food);
        btn_scenery = findViewById(R.id.btn_XiXi_scenery);
        btn_hotel = findViewById(R.id.btn_XiXi_hotel);
        btn_close3 = (Button) findViewById(R.id.xixi_map_close3) ;
        btn_close2 = (Button) findViewById(R.id.xixi_map_close2) ;
        btn_close1 = (Button) findViewById(R.id.xixi_map_close1) ;
        mBehavior_food = findViewById(R.id.bottomSheet1);
        mBehavior_scenery = findViewById(R.id.bottomSheet2);
        mBehavior_hotel = findViewById(R.id.bottomSheet3);

        RecyclerView recyclerView_food = (RecyclerView) mBehavior_food.findViewById(R.id.xixi_map_recyclerview1);
        mBottomSheetBehavior_food = BottomSheetBehavior.from(mBehavior_food);

        RecyclerView recyclerView_scenery = (RecyclerView) mBehavior_scenery.findViewById(R.id.xixi_map_recyclerview2);
        mBottomSheetBehavior_scenery = BottomSheetBehavior.from(mBehavior_scenery);

        RecyclerView recyclerView_hotel = (RecyclerView) mBehavior_hotel.findViewById(R.id.xixi_map_recyclerview3);
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
        recyclerView_food.setHasFixedSize(true);//????????????????????????
        recyclerView_food.setAdapter(new ItemAdapter(createFoodItems(), new ItemAdapter.OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                if (pos == 0){
                    setEatingLat(30.263562,120.065917);
                }else if (pos == 1){
                    setEatingLat(30.253782,120.057864);
                }else if (pos==2){
                    setEatingLat(30.271329,120.097022);
                }else if (pos==3){
                    setEatingLat(30.258186,120.060658);
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
                        recovery(30.27289,120.071528);
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
                    setSceneryLat(30.246914,120.107833);
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
                recovery(30.27289,120.071528);
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
                recovery(30.27289,120.071528);
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        //???activity??????onResume?????????mMapView. onResume ()?????????????????????????????????
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //???activity??????onPause?????????mMapView. onPause ()?????????????????????????????????
        mMapView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //???activity??????onDestroy?????????mMapView.onDestroy()?????????????????????????????????
        mMapView.onDestroy();
        unregisterReceiver(receiver);
    }


    private void registerSDKCheckReceiver(){
        receiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action=intent.getAction();
                if (SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR.equals(action)){
                    Toast.makeText(getApplicationContext(),"????????????",Toast.LENGTH_SHORT).show();
                }else if (SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR.equals(action)){
                    Toast.makeText(getApplicationContext(),"KEY????????????",Toast.LENGTH_SHORT).show();
                }
            }
        };
        IntentFilter filter=new IntentFilter();
        //??????????????????
        filter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        //??????????????????SDK???key????????????
        filter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        registerReceiver(receiver,filter);
    }

    public List<Item> createFoodItems() {
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item(R.drawable.xixibieyuan, "????????????","??????  ?????????"));
        items.add(new Item(R.drawable.nongtaoli, "?????????","??????  ??????"));
        items.add(new Item(R.drawable.waipojia, "?????????","?????? ??????"));
        items.add(new Item(R.drawable.xinrongji, "?????????","?????? ?????????"));
        return items;
    }

    public List<Item> createSceneryItems() {
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item(R.drawable.feilaifeng, "?????????????????????","??????/??????"));
        items.add(new Item(R.drawable.xihutitle, "?????????????????????","??????/??????"));
        items.add(new Item(R.drawable.lingyintitle, "?????????","?????????/?????????"));
        items.add(new Item(R.drawable.leifengta, "???????????????","?????????/????????????"));
        return items;
    }

    public List<Item> createHotelItems() {
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item(R.drawable.huatang, "?????????????????????","?????????"));
        items.add(new Item(R.drawable.shilifenfei, "????????????????????????","?????????"));
        items.add(new Item(R.drawable.yaduo, "????????????","?????????"));
        items.add(new Item(R.drawable.qibo, "??????????????????","?????????"));
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
//???OverlayOptions?????????list
        options.add(option1);
        options.add(option2);
        options.add(option3);
        options.add(option4);
//????????????????????????
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
//???OverlayOptions?????????list
        options.add(option1);
        options.add(option2);
        options.add(option3);
        options.add(option4);
//????????????????????????
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
//???OverlayOptions?????????list
        options.add(option1);
        options.add(option2);
        options.add(option3);
        options.add(option4);
//????????????????????????
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
                //??????Marker??????
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.eat);
                //??????MarkerOption???????????????????????????Marker
                OverlayOptions option = new MarkerOptions()
                        .position(point)
                        .icon(bitmap);
                //??????????????????Marker????????????
                mBaiduMap.addOverlay(option);
                mapStatusUpdate= MapStatusUpdateFactory.newLatLng(new LatLng(x,y));
                mBaiduMap.setMapStatus(mapStatusUpdate);
                //4.   ?????????????????????15
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
                //??????Marker??????
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.hotel);
                //??????MarkerOption???????????????????????????Marker
                OverlayOptions option = new MarkerOptions()
                        .position(point)
                        .icon(bitmap);
                //??????????????????Marker????????????
                mBaiduMap.addOverlay(option);
                mapStatusUpdate= MapStatusUpdateFactory.newLatLng(new LatLng(x,y));
                mBaiduMap.setMapStatus(mapStatusUpdate);
                //4.   ?????????????????????15
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
                //??????Marker??????
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.scenecy);
                //??????MarkerOption???????????????????????????Marker
                OverlayOptions option = new MarkerOptions()
                        .position(point)
                        .icon(bitmap);
                //??????????????????Marker????????????
                mBaiduMap.addOverlay(option);
                mapStatusUpdate= MapStatusUpdateFactory.newLatLng(new LatLng(x,y));
                mBaiduMap.setMapStatus(mapStatusUpdate);
                //4.   ?????????????????????15
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
                //??????MarkerOption???????????????????????????Marker
                OverlayOptions option = new MarkerOptions()
                        .position(target)
                        .icon(bitmap);
                //??????????????????Marker????????????
                mBaiduMap.addOverlay(option);
                //4.   ?????????????????????15
                mapStatusUpdate= MapStatusUpdateFactory.zoomTo(15);
                mBaiduMap.setMapStatus(mapStatusUpdate);
            }
        });
    }

}
