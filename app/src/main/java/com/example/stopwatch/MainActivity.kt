package com.example.stopwatch

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private var seconds = 0
    private var running = false
    private var startTimeInMillis: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds")
            running = savedInstanceState.getBoolean("running")
            startTimeInMillis = savedInstanceState.getLong("startTimeInMillis")
            if (running) {
                runStopwatch()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("seconds", seconds)
        outState.putBoolean("running", running)
        outState.putLong("startTimeInMillis", startTimeInMillis)
    }

    fun onClickStart(view: View) {
        running = true
        startTimeInMillis = System.currentTimeMillis()
        runStopwatch()
    }

    fun onClickStop(view: View) {
        running = false
    }

    fun onClickReset(view: View) {
        running = false
        seconds = 0
        updateTimer()
    }

    private fun runStopwatch() {
        val handler = Handler()
        handler.post(object : Runnable {
            override fun run() {
                updateTimer()
                if (running) {
                    handler.postDelayed(this, 10)
                }
            }
        })
    }

    private fun updateTimer() {
        val txtTime: TextView = findViewById(R.id.txtTime)
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60
        val time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs)
        txtTime.text = time
        if (running) {
            seconds++
        }
    }
}
