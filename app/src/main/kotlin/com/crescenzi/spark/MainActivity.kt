package com.crescenzi.spark

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import java.lang.Thread.sleep

class MainActivity : ComponentActivity() {

    init {
        System.loadLibrary("spark_rust")
    }

    external fun sum(a: Int, b: Int): Int


    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition { sleep(800); false }
        super.onCreate(savedInstanceState)

        Log.e("MY-LOG", "SUM => ${sum(50, 50)}")
    }
}
