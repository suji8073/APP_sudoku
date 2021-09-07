package com.csj.app_sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button  = (Button)findViewById(R.id.play);
        final EditText name = (EditText)findViewById(R.id.name);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().length()!=0) { // 이름을 받았을 경우
                    String user_name = name.getText().toString(); // 사용자 이름
                    Intent start_intent = new Intent(MainActivity.this, play_menu.class);
                    start_intent.putExtra("이름", user_name); // 이름 값 가지고
                    startActivity(start_intent);
                }
                //이름을 받지 않으면 다음 화면으로 이동 불가
            }
        });

    }
}