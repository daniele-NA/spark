package com.crescenzi.spark

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Thread.sleep

class MainActivity : ComponentActivity() {

    init {
        System.loadLibrary("spark_rust")
    }

    external fun sum(a: Int, b: Int): Int
    external fun callApi(): Boolean


    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition { sleep(800); false }
        super.onCreate(savedInstanceState)

        // == CALL NATIVE METHODS == //
        Log.e("MY-LOG", "SUM => ${sum(50, 50)}")
        lifecycleScope.launch(Dispatchers.IO) {
            Log.e("MY-LOG", "CALL-API => ${callApi()}")
        }
    }
}
