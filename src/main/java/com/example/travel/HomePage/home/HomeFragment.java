package com.example.travel.HomePage.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.travel.DataBase.Scenery;
import com.example.travel.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private Context mContext;
    ImageButton main_rank_btn;
    ImageButton main_record_btn;

    private ViewFlipper vFlipperPicture;
    private ViewFlipper vFlipperText;

    //图片数组
    private int[] arrayPic;
    private String[] arrayNotice;
    private List<Scenery> txList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }

    @Override
    public void onStart() {
        arrayPic = new int[] {R.drawable.xixishidi2, R.drawable.changchen1, R.drawable.gugong3,R.drawable.tiananmen1,R.drawable.lingyin1};
        arrayNotice = new String[] {"快乐出行每一天","快乐旅途你我为伴","写探笔记赢红包", "全国风景任你游"};
        super.onStart();
        mContext = getContext();
        initTxs();//下面的初始化方法
        RecyclerView recyclerView = getView().findViewById(R.id.recycler);//找到RecyclerView控件
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);//布局管理器
        recyclerView.setLayoutManager(layoutManager);
        HomeAdapter adapter = new HomeAdapter(txList, mContext,
                new HomeAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(int pos) {
                        if (pos==0)
                            startActivity(new Intent(mContext, XiXi.class));
                        else if (pos==1)
                            startActivity(new Intent(mContext,LingYin.class));
                        else if (pos==2)
                            startActivity(new Intent(mContext,GuGong.class));
                        else if (pos==3)
                            startActivity(new Intent(mContext,TianAnMen.class));
                        else if (pos==4)
                            startActivity(new Intent(mContext,Zoo.class));
                        else if (pos==5)
                            startActivity(new Intent(mContext,ChangCheng.class));
                    }
                });//适配器对象
        recyclerView.setAdapter(adapter);//设置适配器为上面的对象

        main_rank_btn = getView().findViewById(R.id.Total);
        main_rank_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext,Rank.class));
            }
        });

        main_record_btn = getView().findViewById(R.id.record);
        main_record_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext,Record.class));
            }
        });

        vFlipperPicture = getView().findViewById(R.id.picture_show);
        vFlipperText = getView().findViewById(R.id.text_show);
        viewsDataInit();
    }
    private void initTxs() {
        Scenery a = new Scenery("西溪国家湿地公园", "  浙江杭州西溪国家湿地公园位于浙江省杭州市区西部,是中国第一个集城市湿地、农耕湿地、文化湿地于一体的国家级湿地公园。",R.drawable.xixititle);
        txList.add(a);//加入到链表
        Scenery b = new Scenery("灵隐景区", "中国佛教著名的“十刹”之一",R.drawable.lingyintitle);
        txList.add(b);
        Scenery c = new Scenery("故宫博物馆", "世界上现存规模最大、保存最为完整的木质结构古建筑之一，国家5A级旅游景区",R.drawable.gugongtitle);
        txList.add(c);
        Scenery d = new Scenery("天安门广场", "世界上最大的城市广场",R.drawable.tianantitle);
        txList.add(d);
        Scenery e = new Scenery("杭州动物园", "中国七大动物园之一",R.drawable.dongwuyauntitle);
        txList.add(e);
        Scenery f = new Scenery("长城", "中国最长的长城，著名的北京十六景之一",R.drawable.changchentitle);
        txList.add(f);
    }


    public void viewsDataInit() {
        //设置时间
        vFlipperPicture.setFlipInterval(3000);
        //设置进出动画
        vFlipperPicture.setInAnimation(mContext, R.anim.anim_come_in_h);
        vFlipperPicture.setOutAnimation(mContext, R.anim.anim_get_out_h);
        for (int i = 0; i < arrayPic.length; i ++) {
            vFlipperPicture.addView(getImageView(arrayPic[i]));
        }
        vFlipperPicture.startFlipping();

        vFlipperText.setFlipInterval(3000);
        vFlipperText.setInAnimation(mContext, R.anim.anim_come_in_v);
        vFlipperText.setOutAnimation(mContext, R.anim.anim_get_out_v);
        for (int i = 0; i < arrayNotice.length; i ++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_show, null,false);
            TextView tvContent = view.findViewById(R.id.show_content);
            tvContent.setText(arrayNotice[i]);
            vFlipperText.addView(view);
        }
        vFlipperText.startFlipping();
    }

    private ImageView getImageView(int resId){
        ImageView img = new ImageView(mContext);
        img.setBackgroundResource(resId);
        return img;
    }
}