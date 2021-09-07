package com.csj.app_sudoku;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_ extends AppCompatActivity {
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_);
        back = (ImageView)findViewById(R.id.back); // 형변환
        back.setOnClickListener(new View.OnClickListener() { // back 이미지를 클릭하면
            @Override
            public void onClick(View view) {
                finish(); // 전의 화면으로 돌아감
            }
        });


    }
}