package com.csj.app_sudoku;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;
import static android.widget.Toast.makeText;


public class game_Activity extends AppCompatActivity {
    MediaPlayer mediaPlayer; // 배경음악 플레이
    boolean music_check = true; // 배경음악 ON/OFF 체크

    private TextView timer; //타이머
    private static final int INIT = 0; //처음
    private static final int RUN = 1; //실행중
    private static final int PAUSE = 2; //정지

    int timer_check = INIT; // 처음 타이머 상태

    long Timer;
    long Brake_Timer;
    
    TextView board_num[] = new TextView[81]; // 숫자를 채울 TextView
    Integer[] b_num = {R.id.one_1, R.id.one_2, R.id.one_3, R.id.two_1, R.id.two_2, R.id.two_3, R.id.three_1, R.id.three_2, R.id.three_3,
            R.id.one_4, R.id.one_5, R.id.one_6, R.id.two_4, R.id.two_5, R.id.two_6,R.id.three_4, R.id.three_5, R.id.three_6,
            R.id.one_7, R.id.one_8, R.id.one_9, R.id.two_7, R.id.two_8, R.id.two_9, R.id.three_7, R.id.three_8, R.id.three_9,
            R.id.four_1, R.id.four_2, R.id.four_3, R.id.five_1, R.id.five_2, R.id.five_3, R.id.six_1, R.id.six_2, R.id.six_3,
            R.id.four_4, R.id.four_5, R.id.four_6, R.id.five_4, R.id.five_5, R.id.five_6,  R.id.six_4, R.id.six_5, R.id.six_6,
            R.id.four_7, R.id.four_8, R.id.four_9, R.id.five_7, R.id.five_8, R.id.five_9, R.id.six_7, R.id.six_8, R.id.six_9,
            R.id.seven_1, R.id.seven_2, R.id.seven_3, R.id.eight_1, R.id.eight_2, R.id.eight_3,  R.id.nine_1, R.id.nine_2, R.id.nine_3,
            R.id.seven_4, R.id.seven_5, R.id.seven_6, R.id.eight_4, R.id.eight_5, R.id.eight_6,R.id.nine_4, R.id.nine_5, R.id.nine_6,
            R.id.seven_7, R.id.seven_8, R.id.seven_9, R.id.eight_7, R.id.eight_8, R.id.eight_9, R.id.nine_7, R.id.nine_8, R.id.nine_9};

    ImageView num[] = new ImageView[9]; // 숫자 이미지
    Integer[] Rid_num = {R.id.num1, R.id.num2, R.id.num3, R.id.num4, R.id.num5, R.id.num6, R.id.num7, R.id.num8, R.id.num9};

    int check; //스도쿠 정답이 맞는지 확인

    TextView mistake; // 실수 프린트
    int mistake_num = 0; // 오답 count

    public HashMap<int[][], int[][]> xy = new HashMap<>(); // 문제와 정답을 연결하여 저장
    public HashMap<Integer, int[][]> all = new HashMap<>(); //스도쿠 문제와 인덱스를 연결하여 저장
    public int [][] xy_check; // 화면에 출력될 스도쿠 문제
    public int [][] xy_solution; // 화면에 출력될 스도쿠 문제의 정답
    public int blank = 0; // 채우지 못한 공백을 확인하기 위한 변수

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        Intent g_A = getIntent();
        final int[] percent = g_A.getIntArrayExtra("퍼센트");
        final String user_name = g_A.getStringExtra("이름");

        mediaPlayer = MediaPlayer.create(this, R.raw.bgm);
        mediaPlayer.start(); // bgm 플레이

        timer = (TextView) findViewById(R.id.clock); // 타이머 출력할 textview

        switch(timer_check) {
            case INIT: // 최초 실행일 경우
                Timer = SystemClock.elapsedRealtime();
                System.out.println(Timer);
                myTimer.sendEmptyMessage(0);
                timer_check = RUN;
                break;
            case RUN: // 진행중
                myTimer.removeMessages(0);
                Brake_Timer = SystemClock.elapsedRealtime();
                timer_check = PAUSE;
                break;
            case PAUSE: // 정지
                long now = SystemClock.elapsedRealtime();
                myTimer.sendEmptyMessage(0);
                Timer += (now - Brake_Timer);
                timer_check = RUN;
                break;
        }


        for (int i=0; i<81; i++){ // board 형변환
            board_num[i] = (TextView)findViewById(b_num[i]);
        }
        for (int i= 0; i<9; i++){ // 숫자 형변환
            num[i] = (ImageView)findViewById(Rid_num[i]);
        }

        // 문제와 정답 가져오기
        game_Problem game_problem = new game_Problem();
        game_problem.game_add();
        xy_check = game_problem.r_check(); // 문제
        xy_solution = game_problem.r_solution(xy_check); // 정답


        int k=0;
        for (int i=0; i<9; i++){ // 게임판 숫자 추가
            for (int j = 0; j<9; j++){
                if (xy_check[i][j] != 0){
                    int put = xy_check[i][j];
                    board_num[k].setText(String.valueOf(put));
                    k++;
                }
                else {
                    blank++; // 공백 개수를 알기 위함
                    k++;
                }
            }
        }


        mistake = (TextView)findViewById(R.id.mistake); // 오답이면 실수 +1
        final ImageView eraser = (ImageView)findViewById(R.id.eraser);

        for (int i=0; i<board_num.length; i++){

            final int INDEX_BOARD;
            INDEX_BOARD = i;

            board_num[INDEX_BOARD].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (board_num[INDEX_BOARD].getText().length() <= 0) { // 공백일 경우
                        board_num[INDEX_BOARD].setBackgroundColor(Color.parseColor("#FFE4E1")); // 배경색 변경 (클릭했다는 표시)

                        eraser.setOnClickListener(new View.OnClickListener() { // 지우개 클릭할 경우
                            @Override
                            public void onClick(View view) {
                                board_num[INDEX_BOARD].setText(""); // 공백으로 변경
                                board_num[INDEX_BOARD].setBackgroundColor(Color.TRANSPARENT); // 배경색 초기화

                                // 3x3 보드판을 구별하기 위한 색상 조절
                                int b_c = change_color(INDEX_BOARD);
                                if (b_c == 0)
                                    board_num[INDEX_BOARD].setBackground(getResources().getDrawable(R.drawable.s_board_));
                                else if (b_c == 1)
                                    board_num[INDEX_BOARD].setBackground(getResources().getDrawable(R.drawable.s_board));
                            }
                        });

                        for (int j = 0; j < num.length; j++) {
                            final int INDEX_NUM;
                            INDEX_NUM = j;

                            num[INDEX_NUM].setOnClickListener(new View.OnClickListener() { //숫자 이미지를 클릭할 경우
                                @Override
                                public void onClick(View view) {
                                    eraser.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            board_num[INDEX_BOARD].setText(""); // 공백으로
                                            board_num[INDEX_BOARD].setBackgroundColor(Color.TRANSPARENT);

                                            // 3x3 보드판을 구별하기 위한 색상 조절
                                            int b_c = change_color(INDEX_BOARD);
                                            if (b_c == 0)
                                                board_num[INDEX_BOARD].setBackground(getResources().getDrawable(R.drawable.s_board_));
                                            else if (b_c == 1)
                                                board_num[INDEX_BOARD].setBackground(getResources().getDrawable(R.drawable.s_board));

                                        }

                                    });
                                    check = check_num(INDEX_BOARD, INDEX_NUM); // 정답인지 오답인지 확인

                                    board_num[INDEX_BOARD].setText(String.valueOf(INDEX_NUM + 1)); // 일단 추가
                                    if (check == 1) { // 정답이면
                                        board_num[INDEX_BOARD].setBackgroundColor(Color.TRANSPARENT); // 배경색 초기화

                                        // 3x3 보드판을 구별하기 위한 색상 조절
                                        int b_c = change_color(INDEX_BOARD);
                                        if (b_c == 0)
                                            board_num[INDEX_BOARD].setBackground(getResources().getDrawable(R.drawable.s_board_));
                                        else if (b_c == 1)
                                            board_num[INDEX_BOARD].setBackground(getResources().getDrawable(R.drawable.s_board));

                                        blank--; // 공백 하나 줄이기

                                        if (blank == 0) { // 빈칸이 모두 채워진 경우 => 성공
                                            //타이머 종료
                                            myTimer.removeMessages(0);
                                            Brake_Timer = SystemClock.elapsedRealtime();
                                            timer_check = PAUSE;

                                            //배경음악 종료
                                            mediaPlayer.pause();
                                            music_check = false;

                                            // 승리 화면으로 이동
                                            Intent s = new Intent(game_Activity.this, game_success.class);
                                            s.putExtra("퍼센트", percent); // 퍼센트 값 가지고
                                            s.putExtra("이름", user_name); // 이름 값 가지고
                                            s.putExtra("타이머", getTimeOut()); // 시간 값 가지고
                                            startActivity(s);
                                        }

                                    } else { // 오답이면
                                        mistake_num++; // 실수 개수 증가
                                        if (mistake_num < 5) {
                                            mistake.setText("실수 " + mistake_num + " / 5");
                                            Toast.makeText(getApplicationContext(), "실수입니다!!!!", Toast.LENGTH_SHORT).show();

                                            eraser.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    board_num[INDEX_BOARD].setText(""); // 공백으로
                                                    board_num[INDEX_BOARD].setBackgroundColor(Color.TRANSPARENT);

                                                    // 3x3 보드판을 구별하기 위한 색상 조절
                                                    int b_c = change_color(INDEX_BOARD);
                                                    if (b_c == 0)
                                                        board_num[INDEX_BOARD].setBackground(getResources().getDrawable(R.drawable.s_board_));
                                                    else if (b_c == 1)
                                                        board_num[INDEX_BOARD].setBackground(getResources().getDrawable(R.drawable.s_board));

                                                }

                                            });


                                        } else { // 실수가 5번이상이면
                                            Toast.makeText(getApplicationContext(), "게임 패배!!!!", Toast.LENGTH_SHORT).show();

                                            //타이머 종료
                                            myTimer.removeMessages(0);
                                            Brake_Timer = SystemClock.elapsedRealtime();
                                            timer_check = PAUSE;

                                            //배경음악 종료
                                            mediaPlayer.pause();
                                            music_check = false;

                                            //대화상자 출력
                                            AlertDialog.Builder alertDialongBuilder = new AlertDialog.Builder(game_Activity.this);
                                            alertDialongBuilder.setMessage("패배하셨습니다! \n게임을 다시 시작하시겠습니까?");
                                            alertDialongBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    Toast.makeText(getApplicationContext(), "처음 화면으로 돌아갑니다..", Toast.LENGTH_LONG).show();

                                                    //처음 화면으로 돌아감
                                                    Intent i_back = new Intent(game_Activity.this, game_main.class);
                                                    startActivity(i_back);

                                                }
                                            });
                                            alertDialongBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    Toast.makeText(getApplicationContext(), "게임을 종료합니다.", Toast.LENGTH_LONG).show();

                                                    //게임 완전히 종료
                                                    moveTaskToBack(true);
                                                    finishAndRemoveTask();
                                                    android.os.Process.killProcess(android.os.Process.myPid());
                                                }
                                            });
                                            AlertDialog alertDialog = alertDialongBuilder.create();
                                            alertDialog.show();
                                        }
                                    }
                                }
                            });
                        }
                    }
                }
            });
        }
    }

    public int change_color(int INDEX_BOARD) { // 게임 보드판의 색상, 3x3을 구별하기 위함
        int[] check_board = {0, 1, 2, 6, 7, 8, 9, 10, 11, 15, 16, 17, 18, 19, 20,
                24, 25, 26, 30, 31, 32, 39, 40, 41, 48, 49, 50, 54, 55, 56, 60, 61,
                62, 63,64, 65, 69, 70, 71, 72, 73, 74, 78, 79, 80};

        for (int i=0; i<check_board.length; i++){
            if (INDEX_BOARD == check_board[i]) // 배열에 있는 값과 INDEX_BOARD가 일치할 경우
                return 0;
        }
        return 1;
    }


    public int check_num(int INDEX_BOARD, int INDEX_NUM){ // 오답인지 정답인지 확인
        int f = 0;
        for (int i = 0; i<xy_solution.length; i++){
            for (int j = 0; j<xy_solution[0].length; j++){
                if (f == INDEX_BOARD){
                    if (xy_solution[i][j] == INDEX_NUM+1) // 정답이면
                        return 1;
                    else // 오답이면
                        return 0;
                }
                f++;
            }
        }
        return 0;
    }


    Handler myTimer = new Handler(){
        public void handleMessage(Message msg){
            timer.setText(getTimeOut());
            myTimer.sendEmptyMessage(0);
        }
    };

    String getTimeOut(){ // 타이머 view에 출력
        long now = SystemClock.elapsedRealtime(); // 실제 시간
        long outTime = now - Timer;
        String clock = String.format("%02d:%02d", outTime/1000/60, (outTime/1000)%60);

        return clock;
    }

    public void popClick(View view) { // 팝업 메뉴
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.MUSIC:
                        if (music_check) { // 음악이 재생되고 있는 경우
                            mediaPlayer.pause();
                            makeText(getApplicationContext(), "MUSIC OFF", Toast.LENGTH_SHORT).show();
                            menuItem.setTitle("MUSIC ON"); // MUSIC ON 라고 text 변경
                            music_check = false;
                        }
                        else { // 음악이 종료된 경우
                            mediaPlayer.start();
                            makeText(getApplicationContext(), "MUSIC ON", Toast.LENGTH_SHORT).show();
                            menuItem.setTitle("MUSIC OFF"); // MUSIC OFF 라고 text 변경
                        }
                        return true;

                    case R.id.FINISH:

                        //대화상자 출력
                        AlertDialog.Builder alertDialongBuilder = new AlertDialog.Builder(game_Activity.this);
                        alertDialongBuilder.setMessage("앱을 완전히 종료하고 싶으시다면 YES, \n새 게임을 진행하시려면 NO를 클릭해주세요!");
                        alertDialongBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //타이머 종료
                                myTimer.removeMessages(0);
                                Brake_Timer = SystemClock.elapsedRealtime();
                                timer_check = PAUSE;

                                //배경음악 종료
                                mediaPlayer.pause();
                                music_check = false;
                                Toast.makeText(getApplicationContext(), "앱을 종료합니다.", Toast.LENGTH_LONG).show();

                                //게임을 완전히 종료
                                moveTaskToBack(true);
                                finishAndRemoveTask();
                                android.os.Process.killProcess(android.os.Process.myPid());
                            }
                        });
                        alertDialongBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //타이머 종료
                                myTimer.removeMessages(0);
                                Brake_Timer = SystemClock.elapsedRealtime();
                                timer_check = PAUSE;

                                //배경음악 종료
                                mediaPlayer.pause();
                                music_check = false;
                                Toast.makeText(getApplicationContext(), "처음 화면으로 돌아갑니다", Toast.LENGTH_LONG).show();

                                //초기화면으로 돌아감
                                finish();
                            }
                        });
                        AlertDialog alertDialog = alertDialongBuilder.create();
                        alertDialog.show();
                }
                return true;
            }
        });
        popup.show();
    }

}