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

    private Button buttonScan,buttonSave;

    //place = 강의실, seat = 좌석 번호
    private EditText lecture_place, seat_num;
    private TextView textViewResult, check_time;
    private TextView subject_name;
    private ArrayList<HashMap<String, String>> mArrayList;

    // mysql 접속
    private static String TAG = "test";
    private static String IP_ADDRESS ;

    private IntentIntegrator qrScan;

    private String lecture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classinfo);

        lecture_place = (EditText) findViewById(R.id.qr_place);
        seat_num = (EditText) findViewById(R.id.qr_seat);

        check_time = findViewById(R.id.check_time);
        subject_name = (TextView) findViewById(R.id.lecture);
        textViewResult = (TextView)findViewById(R.id.result);
        textViewResult.setMovementMethod(new ScrollingMovementMethod());

        mArrayList = new ArrayList<>();
        mArrayList.clear();
//        InsertData task = new InsertData();


        lecture = getIntent().getStringExtra("lecture");
         String subject_id = getIntent().getStringExtra("subject_id");
        IP_ADDRESS = "https://chlwogh0829.cafe24.com/insert_qr_"+subject_id+".php";
        subject_name.setText(lecture);
//        task.execute(subject_name.getText().toString());

        qrScan = new IntentIntegrator(this);
        buttonScan = (Button) findViewById(R.id.btn_qr_identify);
        buttonScan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                qrScan.setPrompt("Scanning...");
                qrScan.initiateScan();
            }
        });

        buttonSave = findViewById(R.id.btn_enter);
        buttonSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String room = classinfo.this.lecture_place.getText().toString();
                String seat = seat_num.getText().toString();
                String studentId = "60191977";
                String subject_name = getIntent().getStringExtra("lecture");

                classinfo.InsertData task = new classinfo.InsertData();
                task.execute(IP_ADDRESS, room, seat, studentId,subject_name);

                lecture_place.setText(room);
                seat_num.setText(seat);
                check_time.setText("time_stamp");
            }
        });

    }



    private String getTime() {
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss E", Locale.KOREAN);
        mFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        Date mDate = new Date();
        String getTime = mFormat.format(mDate);

        return getTime;
    }
    
     class InsertData extends AsyncTask<String,Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(classinfo.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);

            progressDialog.dismiss();
            textViewResult.setText(result);
            Log.d(TAG, "POST response - "+ result);
        }


        @Override
        protected String doInBackground(String... params) {

            String room = (String)params[1];
            String seat = (String)params[2];
            String student_id = (String)params[3];
            String subject_name =(String)params[4];

            String serverURL = (String)params[0];
            String postParameters = "&seat=" + seat + "&room=" + room  + "&student_id=" + student_id + "&subject_name=" +subject_name;

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString();

            } catch (Exception e) {
                Log.d(TAG, "InsertData: Error ", e);
                return new String("Error: " + e.getMessage());
            }
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {

            //qrcode 가 없으면
            if (result.getContents() == null) {
                Toast.makeText(classinfo.this, "취소!", Toast.LENGTH_SHORT).show();
            } else {

                //qrcode 결과가 있으면
                Toast.makeText(classinfo.this, "스캔완료!", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject obj = new JSONObject(result.getContents());
                    seat_num.setText(obj.getString("seat"));
                    lecture_place.setText(obj.getString("room"));

                } catch (JSONException e) {
                    e.printStackTrace();
                    //Toast.makeText(MainActivity.this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
