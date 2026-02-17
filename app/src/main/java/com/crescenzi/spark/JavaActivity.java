package com.crescenzi.spark;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

// == EXAMPLE WITH JAVA == //
public class JavaActivity extends AppCompatActivity {
    native int sum(int a ,int b);

    @Override
    public void onCreate(Bundle savedInstanceState){
        setTheme(androidx.appcompat.R.style.Theme_AppCompat_DayNight_NoActionBar);
        super.onCreate(savedInstanceState);

        Log.e("MY-LOG", ("SUM => "+sum(50, 50)));
    }
}
