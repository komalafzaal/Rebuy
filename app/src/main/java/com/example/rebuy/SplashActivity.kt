package com.example.rebuy

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rebuy.ui.Login
import java.util.Timer
import java.util.TimerTask


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val task: TimerTask = object : TimerTask() {
            override fun run() {
                val intent = Intent(this@SplashActivity, Login::class.java)
                startActivity(intent)
                finish()
            }
        }
        val t = Timer()
        t.schedule(task, 3000)


    }
}