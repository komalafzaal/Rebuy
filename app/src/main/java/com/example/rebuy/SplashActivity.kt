package com.example.rebuy

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import java.util.Timer
import java.util.TimerTask


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val task: TimerTask = object : TimerTask() {
            override fun run() {
                val intent = Intent(this@SplashActivity, Home::class.java)
                startActivity(intent)
                finish()
            }
        }
        val t = Timer()
        t.schedule(task, 3000)

    }
}