package com.example.travel.HomePage.home;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travel.R;

public class DialogOne extends AppCompatActivity {
    ImageButton btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogone);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = (int) (getWindowManager().getDefaultDisplay().getHeight() * 0.3); // 高度设置为屏幕的0.3
        params.width = (int) (getWindowManager().getDefaultDisplay().getWidth() * 0.8); // 宽度设置为屏幕的0.8
        getWindow().setAttributes(params);
        btn = findViewById(R.id.dialog_close);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogOne.this.finish();
            }
        });
    }
}
