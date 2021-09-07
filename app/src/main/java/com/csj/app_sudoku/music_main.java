package com.csj.app_sudoku;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.google.android.youtube.player.YouTubeBaseActivity;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;


public class music_main extends YouTubeBaseActivity {

    YouTubePlayerView youTubeView;
    YouTubePlayer.OnInitializedListener listener;
    Button start_music;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_main);

        youTubeView = findViewById(R.id.youtubeView);
        listener = new YouTubePlayer.OnInitializedListener(){
            //초기화 성공시
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("CodRK1uj5VE"); //url의 맨 뒷부분 ID값

            }
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }



        };
        start_music = (Button)findViewById(R.id.start_music);
        start_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                youTubeView.initialize("AIzaSyAmkohU9WUmoA-1cGo1yby0545s7XfZaEo", listener); // 유튜브 재생
            }
        });

        Button music_back = (Button)findViewById(R.id.music_back);
        music_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // 전의 화면으로 되돌아감
            }
        });
    }







}
