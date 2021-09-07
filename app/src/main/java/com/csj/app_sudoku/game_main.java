package com.csj.app_sudoku;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class game_main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_main);

        Intent g_m = getIntent();
        final int[] percent = g_m.getIntArrayExtra("퍼센트");
        final String user_name = g_m.getStringExtra("이름"); // 사용자 이름을 기억하기 위해 가져옴

        ImageView light = (ImageView)findViewById(R.id.light);
        light.setOnClickListener(new View.OnClickListener() { // 게임 설명
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(game_main.this, Activity_.class);
                startActivity(intent);
            }
        });

        Button game_start  = (Button)findViewById(R.id.game_start);
        game_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 게임 시작
                Intent start_intent = new Intent(game_main.this, game_Activity.class);
                start_intent.putExtra("퍼센트", percent); // 퍼센트 값 가지고
                start_intent.putExtra("이름", user_name); // 이름 값 가지고
                startActivity(start_intent);
            }
        });
        Button f_back = (Button)findViewById(R.id.f_back);
        f_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 초기 화면으로 돌아가기
                Intent back_menu = new Intent(game_main.this, play_menu.class);
                back_menu.putExtra("퍼센트", percent); // 퍼센트 값 가지고
                back_menu.putExtra("이름", user_name); // 이름 값 가지고
                startActivity(back_menu);
            }
        });
    }
}