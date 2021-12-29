package com.example.travel.HomePage.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travel.MainActivity;
import com.example.travel.R;

import java.util.ArrayList;
import java.util.List;

public class Rank extends Activity {
    List<RankItem> txList = new ArrayList<>();
    Context mContext;
    private ImageButton btn_return;
    private ImageButton btn_explain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rank);
        mContext = getApplicationContext();
        initTxs();
        RecyclerView recyclerView = findViewById(R.id.rank_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RankAdapter adapter = new RankAdapter(txList, mContext, new RankAdapter.OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                if (pos==0){
                    startActivity(new Intent(Rank.this,XiXi.class));
                }else if (pos==1){
                    startActivity(new Intent(Rank.this,Zoo.class));
                }else if (pos==2){
                    startActivity(new Intent(Rank.this,GuGong.class));
                }else if (pos==3){
                    startActivity(new Intent(Rank.this,ChangCheng.class));
                }else if (pos==4){
                    startActivity(new Intent(Rank.this,LingYin.class));
                }
            }
        });
        recyclerView.setAdapter(adapter);
        init();
    }

    private void init(){
        btn_return = findViewById(R.id.rank_return);
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rank.this.finish();
            }
        });

        btn_explain = findViewById(R.id.explain);
        btn_explain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Handler mHandler = new Handler();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(Rank.this,DialogOne.class));
                    }
                });

            }
        });
    }

    private void initTxs(){
        RankItem a = new RankItem(R.drawable.xixititle,"西溪国家湿地公园","浙江省杭州市西湖区天目山路518号周家村主路口","生态资源丰富、自然景观幽雅、文化积淀深厚，值得一游好地方");
        txList.add(a);
        RankItem b = new RankItem(R.drawable.dongwuyauntitle,"杭州野生动物园","杭州市杭富路九龙大道1号","近距离接触动物，感受“人与动物共同拥有一个地球”");
        txList.add(b);
        RankItem c = new RankItem(R.drawable.gugongtitle,"故宫博物馆","北京市东城区景山前街4号","浓郁多姿的满族民族风格,了解古代中国的辉煌历史");
        txList.add(c);
        RankItem d = new RankItem(R.drawable.changchentitle,"慕田峪长城国家级旅游景区","北京市怀柔区渤海镇慕田峪村","地势险峻、气势恢宏、惊艳于世");
        txList.add(d);
        RankItem e = new RankItem(R.drawable.lingyintitle,"灵隐寺","浙江省杭州市西湖区灵隐路法云弄1号","体验佛教文化，感悟人生哲理");
        txList.add(e);
    }
}
