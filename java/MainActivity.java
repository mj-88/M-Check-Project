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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
//학생 출석 체크(메인화면)
public class MainActivity extends AppCompatActivity {

    private TextView textview_time;
    private DrawerLayout drawerLayout;
    private View drawerview;
    private long backpresstime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        View drawerview = findViewById(R.id.drawerview);

        TextView check_sbmenu = findViewById(R.id.check_sbmenu);
        check_sbmenu.setOnClickListener(new View.OnClickListener() {//출석체크 메뉴
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        TextView status_sbmenu = findViewById(R.id.status_sbmenu);
        status_sbmenu.setOnClickListener(new View.OnClickListener() {//시간표 메뉴
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, status.class);
                startActivity(intent);
                finish();
            }
        });
        TextView home_sbmenu = findViewById(R.id.home_sbmenu);
        home_sbmenu.setOnClickListener(new View.OnClickListener() {//홈페이지 메뉴
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, webview.class);
                startActivity(intent);
            }
        });
        TextView info_sbmenu = findViewById(R.id.info_sbmenu);
        info_sbmenu.setOnClickListener(new View.OnClickListener() {//공지사항 메뉴
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, notice.class);
                startActivity(intent);
                finish();
            }
        });
        TextView setting_sbmenu = findViewById(R.id.setting_sbmenu);
        setting_sbmenu.setOnClickListener(new View.OnClickListener() {//환경설정 메뉴
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, setting.class);
                startActivity(intent);
                finish();
            }
        });



        TextView lecture_main = findViewById(R.id.lecture_main);
        lecture_main.setOnClickListener(new View.OnClickListener() {//강의 터치시 화면 이동
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, classinfo.class);
                startActivity(intent);
            }
        });

        textview_time = (TextView) findViewById(R.id.textview_time);
        textview_time.setText(getTime());//시계

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
}