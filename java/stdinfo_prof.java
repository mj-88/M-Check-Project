package com.example.mcheck;


import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
//교수 학생 신상정보 팝업창
public class stdinfo_prof extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_stdinfo_prof);

    }
}