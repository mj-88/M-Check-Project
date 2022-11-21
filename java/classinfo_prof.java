package com.example.mcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

//교수 강의 좌석도
public class classinfo_prof extends AppCompatActivity {
    private Button stdlist;
    private TextView seatnum_1;
    private TextView textview_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classinfo_prof);

        textview_time = (TextView) findViewById(R.id.textview_time);
        textview_time.setText(getTime());//시계

        seatnum_1 = findViewById(R.id.seatnum_1);
        seatnum_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(classinfo_prof.this, stdinfo_prof.class);
                intent.putExtra("stdnum","60171776");
                intent.putExtra("stdname","최진우");
                intent.putExtra("time", "2022-11-01 12:00:00");
                intent.putExtra("seatnum","1");
                startActivityForResult(intent,1);
            }
        });


        stdlist = findViewById(R.id.btn_stdlist);
        stdlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(classinfo_prof.this, stdlist_prof.class);//수강생 명단 버튼 터치시 이동
                startActivity(intent);
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

}