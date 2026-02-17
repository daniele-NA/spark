package com.crescenzi.spark

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// == EXAMPLE WITH KOTLIN == //
class KotlinActivity : AppCompatActivity() {

    init {
        System.loadLibrary("spark_rust")
    }

    external fun callApi(): Boolean


    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        // == CALL NATIVE METHOD == //
        lifecycleScope.launch(Dispatchers.IO) {
            Log.e("MY-LOG", "CALL-API => ${callApi()}")
            startActivity(Intent(this@KotlinActivity, JavaActivity::class.java))
        }


    }
}
