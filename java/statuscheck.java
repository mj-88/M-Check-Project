package com.example.mcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
//학생 출결상황 상세
public class statuscheck extends AppCompatActivity {
  private TextView TextViewResult ;
    private TextView subject_name;
    private ListView mListviewList;

    String TAG_JSON = "m-check";
    String TAG_ROOM = "room";
    String TAG_TIMETABLE = "timetable";


    private String JsonString;
    private ArrayList<HashMap<String, String>> mArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statuscheck);
        mArrayList = new ArrayList<>();

        mListviewList = (ListView)findViewById(R.id.listView_main_list2) ;

        TextViewResult = (TextView) findViewById(R.id.textView_main_result);

        TextView tv = findViewById(R.id.lecture);
        String lecture = getIntent().getStringExtra("lecture");
        tv.setText(lecture);

        subject_name = (TextView) findViewById(R.id.lecture);

        mArrayList.clear();
        GetData task = new GetData();
        task.execute(subject_name.getText().toString());
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



}
