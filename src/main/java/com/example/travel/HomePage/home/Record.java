package com.example.travel.HomePage.home;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.travel.DataBase.Logined;
import com.example.travel.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

public class Record extends Activity {
    private LatLng point1 = new LatLng(30.246914,120.107833);
    private LatLng point2 = new LatLng(30.27289,120.071528);
    private LatLng point3 = new LatLng(39.909652,116.404177);
    private LatLng point4 = new LatLng(40.437442,116.571611);
    private BroadcastReceiver receiver;
    private BottomSheetBehavior mBottomSheetBehavior;
    View mBehavior;
    private Button btn_close1;
    private BottomSheetBehavior mBottomSheetBehavior1;
    View mBehavior1;
    private Button btn_close11;
    private BottomSheetBehavior mBottomSheetBehavior2;
    View mBehavior2;
    private Button btn_close2;
    private BottomSheetBehavior mBottomSheetBehavior3;
    View mBehavior3;
    private Button btn_close13;
    private ImageButton btn_recovery;
    private ImageButton btn_show;
    private MapView mMapView = null;
    BaiduMap mBaiduMap;
    MapStatusUpdate mapStatusUpdate;
    protected LatLng target=new LatLng(30.27289,120.071528);
    ImageButton btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_record);
        mMapView = (MapView) findViewById(R.id.map_record);
        mBaiduMap = mMapView.getMap();
        //普通地图 ,mBaiduMap是地图控制器对象
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        registerSDKCheckReceiver();
        // 3.  设置地图中心点为target

        mapStatusUpdate= MapStatusUpdateFactory.newLatLng(target);
        mBaiduMap.setMapStatus(mapStatusUpdate);
        //4.   设置地图缩放为15
        mapStatusUpdate= MapStatusUpdateFactory.zoomTo(12);
        mBaiduMap.setMapStatus(mapStatusUpdate);
        initData();
        init();
    }

    private void init(){

        btn_recovery = findViewById(R.id.recovery);
        btn_show = findViewById(R.id.show);
        mBehavior = findViewById(R.id.record_bottomSheet1);
        btn_close1 = (Button) findViewById(R.id.record_map_close1) ;
        btn1 = findViewById(R.id.record_map_return);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Record.this.finish();
            }
        });

        RecyclerView recyclerView = (RecyclerView) mBehavior.findViewById(R.id.record_map_recyclerview1);
        mBottomSheetBehavior = BottomSheetBehavior.from(mBehavior);

        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);//不用重新计算大小
        recyclerView.setAdapter(new ItemAdapter(createFoodItems(), new ItemAdapter.OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                if (pos == 0){
                    setLat1(point1,1);
                }else if (pos == 1){
                    setLat2(point2,2);
                }else if (pos==2){
                    setLat3(point3,3);
                }
            }
        }));

        btn_recovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBaiduMap.clear();
                recovery(30.27289,120.071528);
            }
        });

        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    Handler mHandler = new Handler();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            initData();
                        }
                    });
                } else {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
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
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
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

    }

    private void initData(){
        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                List<LatLng> points = new ArrayList<LatLng>();
                List<OverlayOptions> options = new ArrayList<OverlayOptions>();

                points.add(point1);
                points.add(point2);
                points.add(point3);
                points.add(point4);
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.point1);

                BitmapDescriptor bitmap1 = BitmapDescriptorFactory
                        .fromResource(R.drawable.lingyin2);

                BitmapDescriptor bitmap2 = BitmapDescriptorFactory
                        .fromResource(R.drawable.xixishidi3);

                BitmapDescriptor bitmap3 = BitmapDescriptorFactory
                        .fromResource(R.drawable.tiananmen3);

                BitmapDescriptor bitmap4 = BitmapDescriptorFactory
                        .fromResource(R.drawable.changchen3);

                BitmapDescriptor bitmap5 = BitmapDescriptorFactory
                        .fromResource(R.drawable.point1);
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
                /*
                OverlayOptions mOverlayOptions = new PolylineOptions()
                        .width(10)
                        .color(0xAAFF0000)
                        .points(points);
//在地图上绘制折线
//mPloyline 折线对象
                Overlay mPolyline = mBaiduMap.addOverlay(mOverlayOptions);
                 */

            }
        });
    }

    public List<Item> createFoodItems() {
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item(R.drawable.lingyintitle, "灵隐寺","佛教圣地"));
        items.add(new Item(R.drawable.xixititle, "西溪湿地","国家湿地公园"));
        items.add(new Item(R.drawable.tianantitle, "天安门广场",""));
        items.add(new Item(R.drawable.changchentitle, "长城",""));
        return items;
    }


    private void setLat1(final LatLng pointx, final int index){
        mBehavior2 = findViewById(R.id.record_bottomSheet2);
        btn_close2 = (Button) findViewById(R.id.record_map_close2) ;
        mBottomSheetBehavior2 = BottomSheetBehavior.from(mBehavior2);
        mBehavior1 = findViewById(R.id.record_bottomSheet11);
        btn_close11 = (Button) findViewById(R.id.record_map_close11) ;
        mBottomSheetBehavior1 = BottomSheetBehavior.from(mBehavior1);
        mBehavior3 = findViewById(R.id.record_bottomSheet3);
        btn_close13 = (Button) findViewById(R.id.record_map_close3) ;
        mBottomSheetBehavior3 = BottomSheetBehavior.from(mBehavior3);
        final Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mBaiduMap.clear();
                //构建Marker图标
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.point3);
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .position(pointx)
                        .icon(bitmap);
                //在地图上添加Marker，并显示
                mBaiduMap.addOverlay(option);
                mapStatusUpdate= MapStatusUpdateFactory.newLatLng(pointx);
                mBaiduMap.setMapStatus(mapStatusUpdate);
                //4.   设置地图缩放为15
                mapStatusUpdate= MapStatusUpdateFactory.zoomTo(15);
                mBaiduMap.setMapStatus(mapStatusUpdate);
                final BaiduMap.OnMarkerClickListener onMarkerClickListener = new BaiduMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                            mBottomSheetBehavior1.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                                @Override
                                public void onStateChanged(@NonNull View bottomSheet, int newState) {

                                }

                                @Override
                                public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                                }
                            });
                            TextView textView = findViewById(R.id.dialog_two_text);
                            Typeface typeface = Typeface.createFromAsset(getAssets(), "08华康娃娃体W5.TTF");
                            textView.setTypeface(typeface);

                            if (mBottomSheetBehavior2.getState() == BottomSheetBehavior.STATE_EXPANDED)
                            mBottomSheetBehavior2.setState(BottomSheetBehavior.STATE_COLLAPSED);
                            if (mBottomSheetBehavior3.getState() == BottomSheetBehavior.STATE_EXPANDED)
                            mBottomSheetBehavior3.setState(BottomSheetBehavior.STATE_COLLAPSED);
                            mBottomSheetBehavior1.setState(BottomSheetBehavior.STATE_EXPANDED);

                        return false;
                    }
                };
                btn_close11.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBottomSheetBehavior1.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        Handler mHandler = new Handler();
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mBaiduMap.removeMarkerClickListener(onMarkerClickListener);
                                mBaiduMap.clear();
                            }
                        });
                    }
                });
                mBaiduMap.setOnMarkerClickListener(onMarkerClickListener);
            }
        });
    }
    private void setLat3(final LatLng pointx, final int index){
        mBehavior2 = findViewById(R.id.record_bottomSheet2);
        btn_close2 = (Button) findViewById(R.id.record_map_close2) ;
        mBottomSheetBehavior2 = BottomSheetBehavior.from(mBehavior2);
        mBehavior1 = findViewById(R.id.record_bottomSheet11);
        btn_close11 = (Button) findViewById(R.id.record_map_close11) ;
        mBottomSheetBehavior1 = BottomSheetBehavior.from(mBehavior1);
        mBehavior3 = findViewById(R.id.record_bottomSheet3);
        btn_close13 = (Button) findViewById(R.id.record_map_close3) ;
        mBottomSheetBehavior3 = BottomSheetBehavior.from(mBehavior3);
        final Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mBaiduMap.clear();
                //构建Marker图标
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.point3);
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .position(pointx)
                        .icon(bitmap);
                //在地图上添加Marker，并显示
                mBaiduMap.addOverlay(option);
                mapStatusUpdate= MapStatusUpdateFactory.newLatLng(pointx);
                mBaiduMap.setMapStatus(mapStatusUpdate);
                //4.   设置地图缩放为15
                mapStatusUpdate= MapStatusUpdateFactory.zoomTo(15);
                mBaiduMap.setMapStatus(mapStatusUpdate);
                final BaiduMap.OnMarkerClickListener onMarkerClickListener = new BaiduMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        mBottomSheetBehavior3.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                            @Override
                            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                            }

                            @Override
                            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                            }
                        });

                        if (mBottomSheetBehavior1.getState() == BottomSheetBehavior.STATE_EXPANDED)
                            mBottomSheetBehavior1.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        if (mBottomSheetBehavior2.getState() == BottomSheetBehavior.STATE_EXPANDED)
                            mBottomSheetBehavior2.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        mBottomSheetBehavior3.setState(BottomSheetBehavior.STATE_EXPANDED);
                        TextView textView = findViewById(R.id.dialog_four_text);
                        Typeface typeface = Typeface.createFromAsset(getAssets(), "08华康娃娃体W5.TTF");
                        textView.setTypeface(typeface);

                        return false;
                    }
                };
                btn_close13.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBottomSheetBehavior3.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        Handler mHandler = new Handler();
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mBaiduMap.removeMarkerClickListener(onMarkerClickListener);
                                mBaiduMap.clear();
                            }
                        });
                    }
                });
                mBaiduMap.setOnMarkerClickListener(onMarkerClickListener);
            }
        });
    }

    private void setLat2(final LatLng pointx, final int index){
        mBehavior2 = findViewById(R.id.record_bottomSheet2);
        btn_close2 = (Button) findViewById(R.id.record_map_close2) ;
        mBottomSheetBehavior2 = BottomSheetBehavior.from(mBehavior2);
        mBehavior1 = findViewById(R.id.record_bottomSheet11);
        btn_close11 = (Button) findViewById(R.id.record_map_close11) ;
        mBottomSheetBehavior1 = BottomSheetBehavior.from(mBehavior1);
        mBehavior3 = findViewById(R.id.record_bottomSheet3);
        btn_close13 = (Button) findViewById(R.id.record_map_close3) ;
        mBottomSheetBehavior3 = BottomSheetBehavior.from(mBehavior3);
        final Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mBaiduMap.clear();
                //构建Marker图标
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.point3);
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .position(pointx)
                        .icon(bitmap);
                //在地图上添加Marker，并显示
                mBaiduMap.addOverlay(option);
                mapStatusUpdate= MapStatusUpdateFactory.newLatLng(pointx);
                mBaiduMap.setMapStatus(mapStatusUpdate);
                //4.   设置地图缩放为15
                mapStatusUpdate= MapStatusUpdateFactory.zoomTo(15);
                mBaiduMap.setMapStatus(mapStatusUpdate);
                final BaiduMap.OnMarkerClickListener onMarkerClickListener = new BaiduMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        mBottomSheetBehavior2.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                            @Override
                            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                            }

                            @Override
                            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                            }
                        });
                        TextView textView = findViewById(R.id.dialog_three_text);
                        Typeface typeface = Typeface.createFromAsset(getAssets(), "08华康娃娃体W5.TTF");
                        textView.setTypeface(typeface);

                        if (mBottomSheetBehavior1.getState() == BottomSheetBehavior.STATE_EXPANDED)
                            mBottomSheetBehavior1.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        if (mBottomSheetBehavior3.getState() == BottomSheetBehavior.STATE_EXPANDED)
                            mBottomSheetBehavior3.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        mBottomSheetBehavior2.setState(BottomSheetBehavior.STATE_EXPANDED);

                        return false;
                    }
                };
                btn_close2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBottomSheetBehavior2.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        Handler mHandler = new Handler();
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mBaiduMap.removeMarkerClickListener(onMarkerClickListener);
                                mBaiduMap.clear();
                            }
                        });
                    }
                });
                mBaiduMap.setOnMarkerClickListener(onMarkerClickListener);
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
                //4.   设置地图缩放为15
                mapStatusUpdate= MapStatusUpdateFactory.zoomTo(12);
                mBaiduMap.setMapStatus(mapStatusUpdate);
                initData();
                init();
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
}
