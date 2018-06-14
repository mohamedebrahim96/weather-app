package com.example.hello.jsondemo;

import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    TextView textView1;
    TextView textView2;
    EditText editText;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/JennaSue.ttf");
        textView1 = (TextView)findViewById(R.id.text1);
        textView2 = (TextView)findViewById(R.id.text2);
        btn = (Button) findViewById(R.id.btn);
        textView1.setTypeface(typeface);
        textView2.setTypeface(typeface);
        editText = (EditText)findViewById(R.id.edit_text);

        btn.setTypeface(typeface);





    }
    public class Task extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new  URL(urls[0]);
                urlConnection = (HttpURLConnection)url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int date = reader.read();
                while (date != -1 )
                {
                    char current = (char) date;
                    result += current;
                    date = reader.read();
                }
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                String info = jsonObject.getString("weather");
                Log.i("weather content", info);
                JSONArray jsonArray = new JSONArray(info);
                JSONObject jsonPart = jsonArray.getJSONObject(0);
                textView2.setText(jsonPart.getString("main")+"\r\n"+jsonPart.getString("description"));


            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Can't find this City",Toast.LENGTH_LONG).show();
                textView2.setText("");
            }


        }
    }



    public void popUp(View view)
    {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(editText.getWindowToken(),0);


        try {
            String encoder = URLEncoder.encode(editText.getText().toString(),"UTF-8");
            Task task = new Task();
            task.execute("http://api.openweathermap.org/data/2.5/weather?q="+encoder+"&appid=f6d5a143810370c21064d303f5b46828");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Can't find this City",Toast.LENGTH_LONG).show();
        }
    }




    public class DownloadTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpsURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpsURLConnection)url.openConnection();

                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int date = reader.read();
                while (date != -1)
                {
                    char current = (char) date;
                    result += current;
                    date = reader.read();
                }
                return  result;

            } catch (Exception e) {
                e.printStackTrace();
                return  "faild70";
            }
        }
    }
}
