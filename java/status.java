package com.example.mcheck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
//학생 시간표&출결상황
public class status extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private View drawerview;
    private long backpresstime = 0;
    
    private static String TAG = "select_enrollment";

    private static final String TAG_JSON = "select_enrollment";
    private static final String TAG_SUBJECT_ID = "subject_id";
    private static final String TAG_SUBJECT_NAME = "subject_name";


    private TextView mTextViewResult;
    ArrayList<HashMap<String, String>> mArrayList;
    ListView mListViewList;
    String mJsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        View drawerview = findViewById(R.id.drawerview);

        TextView check_sbmenu = findViewById(R.id.check_sbmenu);
        check_sbmenu.setOnClickListener(new View.OnClickListener() {//출석체크 메뉴
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(status.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        TextView home_sbmenu = findViewById(R.id.home_sbmenu);
        home_sbmenu.setOnClickListener(new View.OnClickListener() {//홈페이지 메뉴
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(status.this, webview.class);
                startActivity(intent);
            }
        });
        TextView info_sbmenu = findViewById(R.id.info_sbmenu);
        info_sbmenu.setOnClickListener(new View.OnClickListener() {//공지사항 메뉴
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(status.this, notice.class);
                startActivity(intent);
                finish();
            }
        });
        TextView setting_sbmenu = findViewById(R.id.setting_sbmenu);
        setting_sbmenu.setOnClickListener(new View.OnClickListener() {//환경설정 메뉴
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(status.this, setting.class);
                startActivity(intent);
                finish();
            }
        });

        mListViewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(view.getContext(), statuscheck.class);
                HashMap<String,String> map =(HashMap<String,String>)parent.getItemAtPosition(position);
                String data = map.get(TAG_SUBJECT_NAME);
                intent.putExtra("lecture", data);
                startActivity(intent); //출결상황 상세화면으로 이동
                }
            }
        );

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
    

    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(status.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "response - " + result);

            if (result == null) {
                mTextViewResult.setText(errorString);
            } else {
                mJsonString = result;
                showResult();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            String student_id = params[0];

            String serverURL = "https://chlwogh0829.cafe24.com/select_enrollment_student.php";
            String postParameters = "student_id=" + student_id;

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString().trim();
            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }
        }
    }

    private void showResult() {
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                String subject_id = item.getString(TAG_SUBJECT_ID);
                String subject_name= item.getString(TAG_SUBJECT_NAME);

                HashMap<String, String> hashMap = new HashMap<>();

                hashMap.put(TAG_SUBJECT_ID, subject_id);
                hashMap.put(TAG_SUBJECT_NAME, subject_name);
                mArrayList.add(hashMap);

            }
            ListAdapter adapter = new SimpleAdapter(
                    status.this, mArrayList, R.layout.item_list_enrollment,
                    new String[]{TAG_SUBJECT_ID, TAG_SUBJECT_NAME},
                    new int[]{R.id.textView_list_Subject_id,R.id.textView_list_Subject_Name}
            );
            mListViewList.setAdapter(adapter);

        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }
    }
}
