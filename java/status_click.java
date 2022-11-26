package com.example.mcheck;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

//리스트뷰에서 과목 선택 시 출결 상태 상세화면으로 이동
public class status_click extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list_enrollment);
        
        TextView lecture_status = findViewById(R.id.textView_list_Subject_id);
        lecture_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(status_click.this, statuscheck.class);//출결상황 상세화면으로 이동
                startActivity(intent);
            }
        });
    }
}
