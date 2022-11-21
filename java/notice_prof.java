package com.example.mcheck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

//교수 공지사항
public class notice_prof extends AppCompatActivity {

    private long backpresstime = 0;

    private TextView textview_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_prof);

        textview_time = (TextView) findViewById(R.id.textview_time);
        textview_time.setText(getTime());//시계

        TextView lecture_notice_prof = findViewById(R.id.lecture_notice_prof);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout2);
        View drawerview2 = findViewById(R.id.drawerview2);

        lecture_notice_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(notice_prof.this, notice_prof2.class);
                startActivity(intent);
            }
        });

        TextView list_sbmenu2 = findViewById(R.id.list_sbmenu2);
        list_sbmenu2.setOnClickListener(new View.OnClickListener() {//강의목록 메뉴
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(notice_prof.this, main_prof.class);
                startActivity(intent);
                finish();
            }
        });

        TextView schedule_sbmenu2 = findViewById(R.id.schedule_sbmenu2);
        schedule_sbmenu2.setOnClickListener(new View.OnClickListener() {//휴/보강관리 메뉴
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(notice_prof.this, schedule_prof.class);
                startActivity(intent);
                finish();
            }
        });

        TextView home_sbmenu2 = findViewById(R.id.home_sbmenu2);
        home_sbmenu2.setOnClickListener(new View.OnClickListener() {//홈페이지 메뉴
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(notice_prof.this, webview_prof.class);
                startActivity(intent);
                finish();
            }
        });

        TextView setting_sbmenu2 = findViewById(R.id.setting_sbmenu2);
        setting_sbmenu2.setOnClickListener(new View.OnClickListener() {//환경설정 메뉴
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(notice_prof.this, setting_prof.class);
                startActivity(intent);
                finish();
            }
        });

        drawerLayout.setDrawerListener(listener);
        drawerview2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
    }

    private String getTime(){//시간 표시
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN);
        mFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        Date mDate = new Date();
        String getTime = mFormat.format(mDate);
        return getTime;
    }


    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() > backpresstime + 2000){
            backpresstime = System.currentTimeMillis();
            Toast.makeText(this, "뒤로 버튼을 한번 더 누르면 종료합니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(System.currentTimeMillis() <= backpresstime + 2000){
            finish();
        }
    }

    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    };

}