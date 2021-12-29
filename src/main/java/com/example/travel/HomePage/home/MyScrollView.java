package com.example.travel.HomePage.home;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {
    private OnScrollListener listener;

    public void setOnScrollListener(OnScrollListener listener) {
        this.listener = listener;
    }

    public MyScrollView(Context context) {
        super(context);
    }

    //设置接口
    public interface OnScrollListener{
        void onScroll(int scrollY);
    }
    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX,
                                  boolean clampedY) {
// TODO Auto-generated method stub
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if(listener != null){

            //这里我只传了垂直滑动的距离
            listener.onScroll(scrollY);
            Log.d("Scroll", String.valueOf(scrollY));
        }
    }
    //重写原生onScrollChanged方法，将参数传递给接口，由接口传递出去

}
