package com.example.travel.HomePage.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.travel.MainActivity;
import com.example.travel.R;

import java.util.ArrayList;

public class SceneryInfoActivity extends Activity {
    private ViewPager guide;
    //引导页图片ID数组
    private int[] ImageIds = new int[]{
            R.drawable.xixishidi,R.drawable.image4
    };
    //imageView的集合
    private ArrayList<ImageView> ImageViewList;
    private Button return1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_scenery_info);;

        initViews();//初始化界面控件
        initData();//先初始化数据,然后再设置数据
        guide.setAdapter(new GuideAdapter());//设置数据

    }


    private void initViews() {
        guide = (ViewPager) findViewById(R.id.guide_xixi);
        return1 = findViewById(R.id.btn_fengjing_teturn1);


        return1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SceneryInfoActivity.this, MainActivity.class));
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





}
