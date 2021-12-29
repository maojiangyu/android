package com.example.travel.HomePage.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;

import com.example.travel.R;

public class TianAnMenInformation extends Activity {
    private ImageButton btn1;
    private Button BuyBtn;
    private Button ShowBtn;
    private Button InformBtn;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    MyScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tiananmeninformation);
        init();
    }
    private void init(){
        btn1 = findViewById(R.id.tiananmen_information_return_btn);
        ShowBtn = findViewById(R.id.tiananmen_information_gonglve_btn);
        BuyBtn = findViewById(R.id.tiananmen_information_buy_btn);
        InformBtn = findViewById(R.id.tiananmen_information_tongzhi_btn);
        mScrollView = findViewById(R.id.tiananmen_scrollView);

        btn2 = findViewById(R.id.tian1);
        btn3 = findViewById(R.id.tian2);
        btn4 = findViewById(R.id.tian3);
        btn2.setBackgroundResource(R.drawable.normal);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler mHandler = new Handler();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        TianAnMenInformation.this.finish();
                    }
                });

            }
        });

        InformBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler mHandler = new Handler();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mScrollView.fullScroll(ScrollView.FOCUS_UP);
                    }
                });
            }
        });
        BuyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler mHandler = new Handler();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mScrollView.smoothScrollTo(0,600);
                    }
                });
            }
        });
        ShowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler mHandler = new Handler();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mScrollView.smoothScrollTo(0,1900);
                    }
                });
            }
        });

        mScrollView.setOnScrollListener(new MyScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollY) {
                int i = scrollY;
                if (i<600){
                    Handler mHandler = new Handler();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            btn2.setBackgroundResource(R.drawable.normal);
                            btn3.setBackgroundResource(R.drawable.press);
                            btn4.setBackgroundResource(R.drawable.press);
                            //btn2.setTextColor();
                        }
                    });
                }
                else if (i>=600&&i<1900){
                    Handler mHandler = new Handler();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            btn3.setBackgroundResource(R.drawable.normal);
                            btn2.setBackgroundResource(R.drawable.press);
                            btn4.setBackgroundResource(R.drawable.press);
                        }
                    });
                }
                else {
                    Handler mHandler = new Handler();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            btn4.setBackgroundResource(R.drawable.normal);
                            btn3.setBackgroundResource(R.drawable.press);
                            btn2.setBackgroundResource(R.drawable.press);
                        }
                    });
                }
                Log.d("Scrollx", String.valueOf(scrollY));
            }
        });
    }
}
