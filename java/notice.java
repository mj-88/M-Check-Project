package com.example.mcheck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
//학생 공지사항
public class notice extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private View drawerview;
    private long backpresstime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        View drawerview = findViewById(R.id.drawerview);

        TextView check_sbmenu = findViewById(R.id.check_sbmenu);
        check_sbmenu.setOnClickListener(new View.OnClickListener() {//출석체크 메뉴
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(notice.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        TextView status_sbmenu = findViewById(R.id.status_sbmenu);
        status_sbmenu.setOnClickListener(new View.OnClickListener() {//시간표 메뉴
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(notice.this, status.class);
                startActivity(intent);
                finish();
            }
        });
        TextView home_sbmenu = findViewById(R.id.home_sbmenu);
        home_sbmenu.setOnClickListener(new View.OnClickListener() {//홈페이지 메뉴
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(notice.this, webview.class);
                startActivity(intent);
            }
        });
        TextView setting_sbmenu = findViewById(R.id.setting_sbmenu);
        setting_sbmenu.setOnClickListener(new View.OnClickListener() {//환경설정 메뉴
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(notice.this, setting.class);
                startActivity(intent);
                finish();
            }
        });

        drawerLayout.setDrawerListener(listener);
        drawerview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });


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
}