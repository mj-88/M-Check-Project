package com.example.mcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

//교수 학생 출결상황 상세
public class stdstatus_prof extends AppCompatActivity {

    private TextView textview_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stdstatus_prof);

        textview_time = (TextView) findViewById(R.id.textview_time);
        textview_time.setText(getTime());//시계
    }

    private String getTime(){//시간 표시
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN);
        mFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        Date mDate = new Date();
        String getTime = mFormat.format(mDate);
        return getTime;
    }

}