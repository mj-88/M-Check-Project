package com.example.mcheck;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//학생 출결상황 상세
public class statuscheck extends AppCompatActivity {

    private TextView TextViewResult ;
    private TextView TextViewResult2 ;
    private TextView subject_name;
    private ListView mListviewList;
    private ListView mListviewList2;

    String TAG_JSON = "m-check";
    String TAG_ROOM = "room";
    String TAG_TIMETABLE = "timetable";

    String TAG_TIME_STAMP  ="time_stamp";
    private String JsonString;
    private String JsonString2;
    private ArrayList<HashMap<String, String>> mArrayList2;

    private ArrayList<HashMap<String, String>> mArrayList;
    private String student_id="60191977";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statuscheck);
        mArrayList = new ArrayList<>();
        mArrayList2 = new ArrayList<>();
        mArrayList2.clear();
        mListviewList = (ListView)findViewById(R.id.listView_main_list2) ;
        mListviewList2 = (ListView) findViewById(R.id.listView_statuscheck_list) ;

        TextViewResult = (TextView) findViewById(R.id.textView_main_result);
        TextViewResult2 = (TextView) findViewById(R.id.textView_main_result2);

        TextView tv = findViewById(R.id.lecture);
        String lecture = getIntent().getStringExtra("lecture");
        tv.setText(lecture);

        subject_name = (TextView) findViewById(R.id.lecture);

        mArrayList.clear();
        GetData task = new GetData();
        task.execute(subject_name.getText().toString());

        GetData2 task2 = new GetData2();
        task2.execute(student_id);
    }



    private class GetData extends AsyncTask<String, Void, String> {


        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(statuscheck.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "response - " + result);

            if (result == null) {
                TextViewResult.setText(errorString);
            } else {
                JsonString = result;
                showResult();
            }
        }

        @Override
        protected String doInBackground(String... params) {


              String subject_name = params[0];

            String serverURL = "https://chlwogh0829.cafe24.com/select_lecture.php";
            String postParameters = "subject_name=" + subject_name;

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

    private class GetData2 extends AsyncTask<String, Void, String> {


        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(statuscheck.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "response - " + result);

            if (result == null) {
                TextViewResult2.setText(errorString);
            } else {
                JsonString2 = result;
                showResult2();
            }
        }

        @Override
        protected String doInBackground(String... params) {


            String student_id = params[0];

            String serverURL = "https://chlwogh0829.cafe24.com/time_stamp_c_0715.php";
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

    public void showResult() {
        try {
            JSONObject jsonObject = new JSONObject(JsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                String room = item.getString(TAG_ROOM);
                String timetable = item.getString(TAG_TIMETABLE);

                HashMap<String, String> hashMap = new HashMap<>();

                hashMap.put(TAG_ROOM, room);
                hashMap.put(TAG_TIMETABLE, timetable);
                mArrayList.add(hashMap);
            }
            ListAdapter adapter = new SimpleAdapter(
                    statuscheck.this, mArrayList, R.layout.list_lecture,
                    new String[]{TAG_ROOM,TAG_TIMETABLE},
                    new int[]{R.id.room, R.id.timetable}
            );
            mListviewList.setAdapter(adapter);

        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }
    }
    public void showResult2() {
        try {
            JSONObject jsonObject = new JSONObject(JsonString2);
            JSONArray jsonArray = jsonObject.getJSONArray("m-check2");

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                String time_stamp = item.getString(TAG_TIME_STAMP );

                HashMap<String, String> hashMap = new HashMap<>();

                hashMap.put(TAG_TIME_STAMP , time_stamp);
                mArrayList2.add(hashMap);
            }
            ListAdapter adapter2 = new SimpleAdapter(
                    statuscheck.this, mArrayList2, R.layout.item_list_statuscheck,
                    new String[]{TAG_TIME_STAMP },
                    new int[]{R.id.time_stamp}
            );
            mListviewList2.setAdapter(adapter2);

        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }
    }





}
