package com.example.travel.HomePage.notifications;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travel.ClickedActivity;
import com.example.travel.DataBase.Logined;
import com.example.travel.DataBase.Scenery;
import com.example.travel.DataBase.SceneryChoosed;
import com.example.travel.LoginPage.LoginActivity;
import com.example.travel.MainActivity;
import com.example.travel.R;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private Context mContent;
    private ArrayList<Scenery> sceneryList = new ArrayList<Scenery>();
    private ArrayList<Scenery> sceneryListFromMain = new ArrayList<>();
    private RecyclerView recyclerView;
    LinearLayout.LayoutParams lp;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        return root;
    }
    public void onStart() {
        super.onStart();
        mContent = getContext();

        getData();//获取数据
        //初始化个人信息
        Logined ld = new Logined();
        TextView tv = (TextView)getView().findViewById(R.id.name);
        tv.setText(ld.getName());
        tv = (TextView)getView().findViewById(R.id.code);
        tv.setText(ld.getProgramId());
        tv = (TextView)getView().findViewById(R.id.level);
        tv.setText("LV."+ld.getLevel());
        tv = (TextView)getView().findViewById(R.id.sign);
        tv.setText(ld.getSign());

        ImageView iv = (ImageView)getView().findViewById(R.id.headImg);
        iv.setImageBitmap(ld.getImg());

        recyclerView = getView().findViewById(R.id.recycler3);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContent);
        recyclerView.setLayoutManager(layoutManager);

        final Button btn1 = (Button)getView().findViewById(R.id.buttom1);
        final Button btn2 = (Button)getView().findViewById(R.id.buttom2);
        final Button btn3 = (Button)getView().findViewById(R.id.buttom3);

        if(ld.getIsLogin()){
            updateMsg(1);//初始化
            changeBtn(btn1,btn2,btn3);

            btn1.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateMsg(1);
                    changeBtn(btn1,btn2,btn3);
                }
            });

            btn2.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateMsg(2);
                    changeBtn(btn2,btn1,btn3);
                }
            });

            btn3.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateMsg(3);
                    changeBtn(btn3,btn2,btn1);
                }
            });
        }else{
            btn1.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeBtn(btn1,btn2,btn3);
                }
            });

            btn2.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeBtn(btn2,btn1,btn3);
                }
            });

            btn3.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeBtn(btn3,btn2,btn1);
                }
            });
        }


        final Button setting = (Button)getView().findViewById(R.id.setting);//设置个人信息
        setting.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logined ld = new Logined();
                if(ld.getIsLogin()){
                    Intent i = new Intent(getActivity().getApplicationContext(), SettingActivity.class);
                    startActivity(i);
                }
                else {
                    Intent i = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                }
            }
        });
        final Button reflash = (Button)getView().findViewById(R.id.reflash);//设置个人信息
        reflash.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logined ld = new Logined();
                    Intent i = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                    startActivity(i);
            }
        });
        final ImageView upload = (ImageView)getView().findViewById(R.id.upload);//上传
        upload.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logined ld = new Logined();
                if(ld.getIsLogin()){
                    Intent i = new Intent(getActivity().getApplicationContext(), UploadActivity.class);
                    startActivity(i);
                }
                else {
                    Intent i = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                }
            }
        });
    }
    private void getData(){//从MainActivity获取数据
        sceneryListFromMain = ((MainActivity)getActivity()).getSceneryList();
    }
    private void updateMsg(int jud){//更新recycleList
        if(jud==1)initTxs1();
        else if(jud==2)initTxs2();
        else if(jud==3)initTxs3();
        else return ;
        NotificationsAdapter adapter = new NotificationsAdapter(sceneryList, mContent,
                new NotificationsAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(int pos) {
                        SceneryChoosed scd = new SceneryChoosed();

                        scd.setId(sceneryList.get(pos).getId());
                        scd.setInfo(sceneryList.get(pos).getInfo());
                        scd.setName(sceneryList.get(pos).getName());
                        scd.setClassify(sceneryList.get(pos).getClassify());
                        scd.setImg(sceneryList.get(pos).getImg());
                        scd.setFavorite(sceneryList.get(pos).getFavorite());

                        startActivity(new Intent(mContent, ClickedActivity.class));
                    }
                });
        recyclerView.setAdapter(adapter);
    }
    private void changeBtn(Button btnChange,Button btnRe1,Button btnRe2){//改变按钮样式
        lp= (LinearLayout.LayoutParams) btnChange.getLayoutParams();
        lp.height=110;
        btnChange.setLayoutParams(lp);
        lp= (LinearLayout.LayoutParams) btnRe1.getLayoutParams();
        lp.height=120;
        btnRe1.setLayoutParams(lp);
        lp= (LinearLayout.LayoutParams) btnRe2.getLayoutParams();
        lp.height=120;
        btnRe2.setLayoutParams(lp);
    }
    private void initTxs1() {
        sceneryList = new ArrayList<>();
        for(int i= 0; i<sceneryListFromMain.size();i++){
            if(sceneryListFromMain.get(i).getClassify()>=6){
                sceneryList.add(sceneryListFromMain.get(i));
            }
        }
    }
    private void initTxs2() {
        sceneryList = new ArrayList<>();
        for(int i= 0; i<sceneryListFromMain.size();i++){
            if(sceneryListFromMain.get(i).getClassify()==5){
                sceneryList.add(sceneryListFromMain.get(i));
            }
        }
    }
    private void initTxs3() {
        sceneryList = new ArrayList<>();
        for(int i= 0; i<sceneryListFromMain.size();i++){
            if(sceneryListFromMain.get(i).getClassify()>=4&&sceneryListFromMain.get(i).getClassify()<6){
                sceneryList.add(sceneryListFromMain.get(i));
            }
        }
    }
}