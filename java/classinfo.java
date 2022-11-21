package com.example.mcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
//학생 출석체크 상세
public class classinfo extends AppCompatActivity {

    Button btn_enter, btn_qr_idnetify;

    EditText seatnum_enter;

    private static String TAG = "test";
    private static String IP_ADDRESS = "https://chlwogh0829.cafe24.com/";

    //private IntentIntegrator qrscan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classinfo);

        TextView textview_time = findViewById(R.id.textview_time);
        textview_time.setText(getTime());

        //qrscan = new IntentIntegrator(this);

        //btn_qr_idnetify.setOnClickListener(new View.OnClickListener() {
           //@Override
            //public void onClick(View view) {
                //qrscan.setPrompt("Scanning...");
                //qrscan.initiateScan();
            //}
        //});
    }



    private String getTime() {
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss E", Locale.KOREAN);
        mFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        Date mDate = new Date();
        String getTime = mFormat.format(mDate);

        return getTime;
    }
}