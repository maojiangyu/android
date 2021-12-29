package com.example.travel.GuideViewPage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.travel.LoginPage.LoginActivity;
import com.example.travel.R;

import java.util.ArrayList;

public class GuideViewActivity  extends Activity {

    private ViewPager guide;
    //引导页图片ID数组
    private int[] ImageIds = new int[]{
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4};
    //imageView的集合
    private ArrayList<ImageView> ImageViewList;
    private LinearLayout container;
    private ImageView red_point;
    private int PointDis;
    private Button btn_start;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide_view);

        initViews();//初始化界面控件
        initData();//先初始化数据,然后再设置数据
        guide.setAdapter(new GuideAdapter());//设置数据

    }


    private void initViews() {
        guide = (ViewPager) findViewById(R.id.guide);
        container = (LinearLayout) findViewById(R.id.container);
        red_point = (ImageView) findViewById(R.id.red_point);
        btn_start = (Button) findViewById(R.id.btn_start);

        //对ViewPager页面滑动的监听
        guide.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override //页面滑动过程中的回调方法
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("------>" + "当前位置:", position + "");
                Log.d("------>" + "偏移百分比:", positionOffset + "");

                //更新小红点的距离=移动百分比*两个原点间的间距+当前位置
                int leftMargin = (int) (PointDis * positionOffset) + position * PointDis;
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                        red_point.getLayoutParams(); //拿到小红点的布局参数
                layoutParams.leftMargin = leftMargin; //修改小红点的布局参数
                red_point.setLayoutParams(layoutParams); //重新设置小红点的布局参数

            }

            @Override //某个页面被选中
            public void onPageSelected(int position) {
                //最后一个页面才显示"开始体验的按钮"
                if (position==ImageViewList.size()-1){ //灵活代码
                    //只在引导页面的最后一页显示按钮
                    btn_start.setVisibility(View.VISIBLE);
                }else{
                    //其余引导页面不显示按钮
                    btn_start.setVisibility(View.INVISIBLE);
                }

            }

            @Override //页面状态发生变化 (滑-->不滑)
            public void onPageScrollStateChanged(int state) {
            }
        });


        //监听layout方法结束,位置确定好了之后,再计算值
        red_point.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //layout方法执行结束的回调

                //移除监听避免重复回调
                red_point.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                /*计算两个圆点的距离 第二个圆点的left值-第一个圆点的left值
                 * measures->layout(确定位置)->draw Acivity的onCreate方法结束后才会走这个流程
                 * */
                PointDis = container.getChildAt(1).getLeft() - container.getChildAt(0).getLeft();
                Log.d("------>" + "计算两个圆点的距离", PointDis + "");
            }
        });

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuideViewActivity.this, LoginActivity.class));
               /* startActivity(new Intent(getApplicationContext(),MainActivity.class));
                //表示已经不是第一次进入,跟新SP
                PrefUtils.setBoolean(getApplicationContext(),ConstantValues.IS_FIRST_ENTER,false);
                //结束引导界面
                finish();*/

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
            point.setImageResource(R.drawable.point);//设置图片<shape>

            //初始化布局参数,宽高包裹内容,父控件是谁就申明谁的布局参数
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            if (i > 0) {
                //从第二个点开始设置原点之间的间隔
                layoutParams.leftMargin = 30;
            }
            point.setLayoutParams(layoutParams);//设置布局参数

            container.addView(point);//给容器添加原点

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
