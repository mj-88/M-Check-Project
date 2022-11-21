package com.example.mcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

//교수 강의 수강생 명단
public class stdlist_prof extends AppCompatActivity {

    private TextView textview_time;
    TextView std_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stdlist_prof);

        textview_time = (TextView) findViewById(R.id.textview_time);
        textview_time.setText(getTime());//시계

        std_name = findViewById(R.id.std_name);
        std_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(stdlist_prof.this, stdstatus_prof.class);
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