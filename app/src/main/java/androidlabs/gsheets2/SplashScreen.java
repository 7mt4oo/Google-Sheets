package androidlabs.gsheets2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.Calendar;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
/*
        Calendar calendar = Calendar.getInstance();
        int currentDay = calendar.get(calendar.DAY_OF_MONTH);
        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        int lastDay = settings.getInt("day", 0);

        if (lastDay != currentDay) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("day", currentDay);
            editor.commit();

            Toast.makeText(this, "Welcome", Toast.LENGTH_LONG).show();
*/
            Thread myThread = new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(4000);
                        Intent intent = new Intent(getApplicationContext(), MainPage.class);
                        startActivity(intent);
                        finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            myThread.start();
        }

    }
