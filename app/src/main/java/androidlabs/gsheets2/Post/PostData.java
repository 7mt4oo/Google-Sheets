package androidlabs.gsheets2.Post;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.journeyapps.barcodescanner.Util;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import androidlabs.gsheets2.R;

public class PostData extends AppCompatActivity {
    private ProgressDialog progress;

    TextView tvName;
    TextView tvCountry;
    Button button;
    String name;
    String country;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);

        button=(Button)findViewById(R.id.btn_signup);
        tvName=(EditText)findViewById(R.id.input_name);
        tvCountry=(EditText)findViewById(R.id.input_Classroom);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               name = tvName.getText().toString();
                country=tvCountry.getText().toString();
                if (tvName.getText().toString().trim().length() <= 0) {
                    Toast.makeText(PostData.this, "Enter Your Name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (tvCountry.getText().toString().trim().length() <= 0) {
                    Toast.makeText(PostData.this, "Enter Your Classroom!", Toast.LENGTH_SHORT).show();
                    return;
                }

                new SendRequest().execute();
             //   Toast.makeText(PostData.this, "Sucess", Toast.LENGTH_SHORT).show();

                finish();

            }

        }   );

        }






    public class SendRequest extends AsyncTask<String, Void, String> {


        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try{
                //Change your web app deployed URL or u can use this for attributes (name, country)
                URL url = new URL("https://script.google.com/macros/s/AKfycbxWJTpZdlK0oLU3hao4J0YgCcc_bBvU8g8pOtt1RE_jJaT74mFW/exec");
              
                JSONObject postDataParams = new JSONObject();

                //int i;
                //for(i=1;i<=70;i++)


                //    String usn = Integer.toString(i);

                String id= "1zEADt-4WT88wyX89g7id9aspRo3OgbHDuUpptawf4sw";

                postDataParams.put("name",name);
                postDataParams.put("country",country);
                postDataParams.put("id",id);


                Log.e("params",postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                }
                else {
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), result,
                    Toast.LENGTH_LONG).show();

        }
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

}
