package com.csj.app_sudoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


class Myview extends View {
    private Path path = new Path();
    private Paint paint = new Paint();


    public Myview(Context context) {
        super(context);
        setBackgroundColor(Color.parseColor("#E0FFFF")); // 배경색
    }

    public Myview(Context context, AttributeSet atts) {
        super(context, atts);
        setBackgroundColor(Color.parseColor("#E0FFFF")); // 배경색

        paint.setAntiAlias(true); //매끄럽게 그리기
        paint.setStrokeWidth(10f); // 선의 굵기 지정
        paint.setStyle(Paint.Style.STROKE); // 채우기 없이 테두리만 그리기
        paint.setColor(Color.parseColor("#000000")); // paint 색상 지정

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, paint); // 현재까지 경로 모두 그리기
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //현재 좌표
        float eventX = event.getX();
        float eventY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // 눌렀을 경우
                path.moveTo(eventX, eventY); // 경로에 위치 저장
                return true;
            case MotionEvent.ACTION_MOVE: // 누르고 움직일 경우
                path.lineTo(eventX, eventY);// 경로에 직선 그리기
                break;
            case MotionEvent.ACTION_UP: // 화면에서 뗐을 경우
                break;
            default:
                return false;
        }
        invalidate(); // onDraw 호출
        return true;
    }
}

