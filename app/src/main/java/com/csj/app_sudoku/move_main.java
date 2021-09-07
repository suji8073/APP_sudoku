package com.csj.app_sudoku;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class move_main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.move_main);

        GridView gridView = (GridView)findViewById(R.id.gridview1); // 어댑터뷰 형 변환

        MyGridView gv = new MyGridView(this); // BaseAdapter를 상속받는 커스텀 어댑터 생성
        gridView.setAdapter(gv); // Adapter를 AdapterView에 연결

        ImageView back = (ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // 전 화면으로 되돌아가기
            }
        });

    }

    public class MyGridView extends BaseAdapter{
        Context context;

        public MyGridView(Context c) {
            context = c;
        }
        public int getCount() { //전체 그림의 갯수
            return posterID.length;
        }
        public Object getItem(int position){ return null; }
        public long getItemId(int position){ return 0; }

        Integer[] posterID = {R.drawable.move_1,R.drawable.move_2,R.drawable.move_3, R.drawable.move_4,R.drawable.move_5,R.drawable.move_6};
        String[] move_title = {"1.머리 두드리기", "2. 배꼽 누르기", "3. 뇌파진동명상", "4. 운동하기", "5. 언어 공부하기", "6. 독서하기"};
        String[] move_message = {"1. 뇌를 자극하는 느낌으로 머리 위쪽과 옆쪽을 손가락을 세워 골고루 꾹꾹 눌러준다.\n2. 시원한 느낌이 들 때까지 가볍게 눌러주면서 호흡은 가능한 길게 내쉰다. ",
                "1. 손가락이나 배꼽 힐링기를 배꼽에 넣는다.\n2. 3분에서 5분간 적당한 속도와 강도로 배꼽을 눌러 자극한다.",
                "1. 눈을 감고 도리도리하듯 머리를 좌우로 천천히 흔든다.\n2. 자연스럽게 리듬을 타면서 머리를 크게 좌우, 앞뒤로 움직인다.\n3. 3~5분간 진동을 느끼면서 머리에서 상체, 허리, 하체 전체적으로 흔들어준다.",
                "걷기와 산책 등 여러가지 운동은 우리의 머리를 더 맑게 해준다. 우리가 운동할 때 몸 전체의 혈압과 혈류가 증가하여 뇌가 잘 활동하도록 해준다.",
                "새로운 언어를 배우는 것은 여러모로 효과적인 활동이다.우리는 새로운 언어를 배울 때 대뇌 피질이 활성화되는데, 이는 듣는 것 또는 이해하는 것 등과 관련된 부위이다.",
                "독서는 거의 모든 영역에 자극을 주어 뇌 기능을 활성화 시킨다. 눈에 보이지 않는 책 속 장면이나 인물을 상상하고 스스로 창조하는 과정에서 뇌의 모든 영역을 사용해 사고 능력을 총제적으로 향상시킨다. "};

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(380, 380));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(20, 20, 20, 20);

            imageView.setImageResource(posterID[position]);

            final int pos = position;
            imageView.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){
                    AlertDialog.Builder dlg = new AlertDialog.Builder(move_main.this);

                    dlg.setTitle(move_title[pos]);  // pos에 따라 맞는 제목 출력
                    dlg.setMessage(move_message[pos]);  // pos에 따라 맞는 메세지 출력
                    dlg.setNegativeButton("닫기", null);
                    dlg.show(); // 보여주기기
               }
            });
            return imageView;

        }

    }
}
