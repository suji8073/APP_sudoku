package com.csj.app_sudoku;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class play_menu extends AppCompatActivity {
    TextView p, user_;
    public int[] percent_check; // 퍼센트를 알아내기 위한 배열
    public int percent; // 퍼센트를 저장
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_menu);

        percent_check = new int[]{0, 0, 0, 0}; // 배열 생성

        Intent p_m = getIntent();
        final String user_name = p_m.getStringExtra("이름"); // 사용자 이름 받아오기
        final int[] get_p = p_m.getIntArrayExtra("퍼센트"); // 다른 화면 이동했을 시에 퍼센트 받아오기

        TextView name = (TextView) findViewById(R.id.view_name);
        name.setText(user_name + " 님"); // 화면에 사용자 이름 출력

        p = (TextView) findViewById(R.id.percent);
        user_ = (TextView) findViewById(R.id.user_);

        if (get_p != null) // 다른 인텐트로부터 퍼센트 값을 받으면
            percent_print(get_p); // 퍼센트 값을 출력하기 위한 함수 호출

        Button food = (Button) findViewById(R.id.food);
        food.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                percent_check[0] = 1; // 퍼센트를 올려주기 위해 배열에 1 대입(초기값은 0)
                percent_print(percent_check);
                Intent food_main = new Intent(play_menu.this, food_main.class);
                startActivity(food_main);
            }
        });
        Button game = (Button) findViewById(R.id.game);
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                percent_check[1] = 1; // 퍼센트를 올려주기 위해 배열에 1 대입(초기값은 0)
                percent_print(percent_check);

                Intent game_main = new Intent(play_menu.this, game_main.class);
                game_main.putExtra("퍼센트", percent_check); // 이름 값 가지고
                game_main.putExtra("이름", user_name); // 이름 값 가지고
                startActivity(game_main);
            }
        });
        Button music = (Button) findViewById(R.id.music);
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                percent_check[2] = 1; // 퍼센트를 올려주기 위해 배열에 1 대입(초기값은 0)
                percent_print(percent_check);
                Intent music_main = new Intent(play_menu.this, music_main.class);
                startActivity(music_main);
            }
        });

        Button move = (Button) findViewById(R.id.move);
        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                percent_check[3] = 1; // 퍼센트를 올려주기 위해 배열에 1 대입(초기값은 0)
                percent_print(percent_check);
                Intent move_main = new Intent(play_menu.this, move_main.class);
                startActivity(move_main);
            }
        });

        Button app_finish = (Button) findViewById(R.id.app_finish);
        app_finish.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {

                //게임을 완전히 종료
                moveTaskToBack(true);
                finishAndRemoveTask();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });

    }

    public void percent_print(int[] percent_check){
        int check = 0; // 메뉴를 다 클릭했는지 확인하기 위한 변수

        for (int i=0; i<percent_check.length; i++){ // 배열의 크기 4만큼 반복문
            if (percent_check[i] == 1) // 1이면 그 메뉴를 적어도 한번은 다 들어가봤다는 의미
                check++;
        }
        switch(check){ // check의 개수에 따라 percent 값 변경 후 출력
            case 1:
                p.setText("25/100%");
                percent = 25;
                break;
            case 2:
                p.setText("50/100%");
                percent = 50;
                break;
            case 3:
                p.setText("75/100%");
                percent = 75;
                break;
            case 4:
                p.setText("100/100%");
                percent = 100;
                user_.setText("두뇌 힐링을 100% 완료하셨습니다!\n웃음은 엔드로핀 및 여러 긍정적인 물질을 생산해줍니다. \n 항상 웃음 넘치는 하루 보내세요 :)");
                break;
        }
    }
}
