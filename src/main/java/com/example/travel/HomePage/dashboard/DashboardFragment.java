package com.example.travel.HomePage.dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travel.ClickedActivity;
import com.example.travel.DataBase.Scenery;
import com.example.travel.DataBase.SceneryChoosed;
import com.example.travel.HomePage.home.XiXi;
import com.example.travel.MainActivity;
import com.example.travel.R;

import java.util.ArrayList;

public class DashboardFragment extends Fragment  {

    private DashboardViewModel dashboardViewModel;
    private Context mContent;
    private ArrayList<Scenery> sceneryList = new ArrayList<>();
    private ArrayList<Scenery> sceneryListFromMain = new ArrayList<>();
    private RecyclerView recyclerView;
    TextPaint tp;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        return root;
    }
    public void onStart() {
        super.onStart();

        mContent = getContext();
        recyclerView = getView().findViewById(R.id.recycler2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContent);
        recyclerView.setLayoutManager(layoutManager);

        getData();
        //初始化
        final Button btn1 = (Button)getView().findViewById(R.id.buttom1);
        final Button btn2 = (Button)getView().findViewById(R.id.buttom2);
        final Button btn3 = (Button)getView().findViewById(R.id.buttom3);

        updateMsg(1);
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
    }
    private void getData(){
        sceneryListFromMain = ((MainActivity)getActivity()).getSceneryList();
    }//从MainActivity获取数据
    private void updateMsg(int jud){//更新recycleView数据
        if(jud==1)initTxs1();
        else if(jud==2)initTxs2();
        else if(jud==3)initTxs3();
        else return ;
        DashboardAdapter adapter = new DashboardAdapter(sceneryList, mContent,
                new DashboardAdapter.OnItemClickListener() {
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
    //改变选中按钮样式
    private void changeBtn(Button btnChange, Button btnRe1, Button btnRe2){//改变按钮样式
        tp = btnChange.getPaint();
        tp.setFakeBoldText(true);
        btnChange.setTextSize(16);
        tp = btnRe1.getPaint();
        tp.setFakeBoldText(false);
        btnRe1.setTextSize(15);
        tp = btnRe2.getPaint();
        tp.setFakeBoldText(false);
        btnRe2.setTextSize(15);
    }
    private void initTxs1() {
        sceneryList = new ArrayList<>();
        for(int i= 0; i<sceneryListFromMain.size();i++){
            if(sceneryListFromMain.get(i).getClassify()>=1){
                sceneryList.add(sceneryListFromMain.get(i));
            }
        }
    }
    private void initTxs2() {
        sceneryList = new ArrayList<>();
        for(int i= 0; i<sceneryListFromMain.size();i++){
            if(sceneryListFromMain.get(i).getClassify()>=2){
                sceneryList.add(sceneryListFromMain.get(i));
            }
        }
    }
    private void initTxs3() {
        sceneryList = new ArrayList<>();
        for(int i= 0; i<sceneryListFromMain.size();i++){
            if(sceneryListFromMain.get(i).getClassify()>=3){
                sceneryList.add(sceneryListFromMain.get(i));
            }
        }
    }

}