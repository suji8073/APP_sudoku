package com.csj.app_sudoku;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class game_success extends AppCompatActivity {
    Button game_back, game_finish;
    MediaPlayer s_mediaPlayer; // 승리음악 플레이
    TextView win_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_success);

        s_mediaPlayer = MediaPlayer.create(this, R.raw.success); // 승리 bgm
        s_mediaPlayer.start(); // bgm 재생

        Intent g_s = getIntent();
        String w_time = g_s.getStringExtra("타이머"); //타이머 가져오기
        final int[] percent = g_s.getIntArrayExtra("퍼센트");
        final String user_name = g_s.getStringExtra("이름");

        win_time = (TextView)findViewById(R.id.win_time);
        win_time.setText(w_time); // 타이머 출력

        game_back = (Button)findViewById(R.id.game_back);
        game_finish = (Button)findViewById(R.id.game_finish);

        game_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 게임 다시 하기
                //배경음악 종료
                s_mediaPlayer.pause();
                Intent back_intent = new Intent(game_success.this, game_main.class);
                back_intent.putExtra("퍼센트", percent); // 퍼센트 값 가지고
                back_intent.putExtra("이름", user_name); // 이름 값 가지고
                startActivity(back_intent);
            }
        });

        game_finish.setOnClickListener(new View.OnClickListener() { // 프로그램 종료
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) { // 게임 완전히 종료

                Intent b_intent = new Intent(game_success.this, play_menu.class);
                b_intent.putExtra("퍼센트", percent); // 퍼센트 값 가지고
                b_intent.putExtra("이름", user_name); // 이름 값 가지고
                startActivity(b_intent);
            }
        });
    }
}