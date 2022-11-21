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

//학생 환경설정
public class setting extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private View drawerview;
    private long backpresstime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        View drawerview = findViewById(R.id.drawerview);

        TextView check_sbmenu = findViewById(R.id.check_sbmenu);
        check_sbmenu.setOnClickListener(new View.OnClickListener() {//출석체크 메뉴
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(setting.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        TextView status_sbmenu = findViewById(R.id.status_sbmenu);
        status_sbmenu.setOnClickListener(new View.OnClickListener() {//시간표 메뉴
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(setting.this, status.class);
                startActivity(intent);
                finish();
            }
        });

        TextView home_sbmenu = findViewById(R.id.home_sbmenu);
        home_sbmenu.setOnClickListener(new View.OnClickListener() {//홈페이지 메뉴
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(setting.this, webview.class);
                startActivity(intent);
            }
        });

        TextView info_sbmenu = findViewById(R.id.info_sbmenu);
        info_sbmenu.setOnClickListener(new View.OnClickListener() {//공지사항 메뉴
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(setting.this, notice.class);
                startActivity(intent);
                finish();
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
        //DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        //if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            //drawerLayout.closeDrawer(GravityCompat.START);//뒤로가기 누를때 사이드바 메뉴 종료
        //}else {
            //.onBackPressed();
        //}
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