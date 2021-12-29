package com.example.travel.HomePage.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.travel.R;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.List;

public class LingYin extends Activity {
    private ViewPager guide;
    //引导页图片ID数组
    private int[] ImageIds = new int[]{
            R.drawable.lingyin2,R.drawable.lingyin1,R.drawable.lingyin3
    };
    //imageView的集合
    private ArrayList<ImageView> ImageViewList;
    private ImageButton return1;

    private List<NearItem> txList = new ArrayList<>();

    private ImageButton mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.lingyin);

        initViews();//初始化界面控件
        initData();//先初始化数据,然后再设置数据
        guide.setAdapter(new LingYin.GuideAdapter());//设置数据

        initTxs();//下面的初始化方法
        RecyclerView recyclerView = findViewById(R.id.LingYin_recyclerView2);//找到RecyclerView控件
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);//布局管理器
        recyclerView.setLayoutManager(layoutManager);
        NearAdapter adapter = new NearAdapter(txList, LingYin.this, new NearAdapter.OnItemClickListener() {
            //@Override
            public void onClick(int pos) {
                //if (pos==0)
                //startActivity(new Intent(xihushidi.this,fenfjing.class));
            }
        });//适配器对象
        recyclerView.setAdapter(adapter);//设置适配器为上面的对象

        init();
    }
    private void init(){
        mBtn = findViewById(R.id.LingYinMapBtn);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LingYin.this,LinYinMap.class));
            }
        });
    }

    private void initViews() {
        guide = (ViewPager) findViewById(R.id.guide_LingYin);
        return1 = findViewById(R.id.btn_LingYin_return);


        return1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LingYin.this.finish();
            }
        });
        //对ViewPager页面滑动的监听
        guide.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override //页面滑动过程中的回调方法
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("------>" + "当前位置:", position + "");
                Log.d("------>" + "偏移百分比:", positionOffset + "");

            }

            @Override //某个页面被选中
            public void onPageSelected(int position) {
                //最后一个页面才显示"开始体验的按钮"


            }


            @Override //页面状态发生变化 (滑-->不滑)
            public void onPageScrollStateChanged(int state) {
            }
        });


    }

    //初始化数据
    private void initData() {
        ImageViewList = new ArrayList<ImageView>();
        for (int i = 0; i < ImageIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(ImageIds[i]);//通过设置背景,可以让宽高填充布局
            ImageViewList.add(imageView);

            //初始化底部小圆点
            ImageView point = new ImageView(this);

            //初始化布局参数,宽高包裹内容,父控件是谁就申明谁的布局参数
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            if (i > 0) {
                //从第二个点开始设置原点之间的间隔
                layoutParams.leftMargin = 10;
            }
            point.setLayoutParams(layoutParams);//设置布局参数


        }
    }


    class GuideAdapter extends PagerAdapter {

        @Override //返回Item的个数
        public int getCount() {
            return ImageViewList.size();
        }

        @Override //固定写法
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override //初始化Item布局
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = ImageViewList.get(position);
            container.addView(imageView);
            return imageView;

        }


        @Override //销毁Item
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private void initTxs() {
        NearItem a = new NearItem("灵隐飞来峰景区", R.drawable.lingyintitle,"      4.5km","4.2","石刻造像 自然奇观");
        txList.add(a);//加入到链表
        NearItem b = new NearItem("西湖风景区", R.drawable.xihutitle,"      7.7km","4.5","流连忘返 江南好景");
        txList.add(b);//加入到链表
        NearItem c = new NearItem("杭州野生动物世界", R.drawable.dongwuyauntitle,"      17.5km","4.3","自然生态 尽收眼底");
        txList.add(c);//加入到链表
        NearItem d = new NearItem("西溪国家湿地公园", R.drawable.image4,"4.5km","4.2","湿地文化 感悟自然");
        txList.add(d);//加入到链表
    }

    public void OnClickToLingYinInformation(View view){
        startActivity(new Intent(LingYin.this,LinYinInformation.class));
    }
}
