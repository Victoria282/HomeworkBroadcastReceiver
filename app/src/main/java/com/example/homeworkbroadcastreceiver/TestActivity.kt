package com.example.homeworkbroadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.homeworkbroadcastreceiver.Service.CheckTime
import com.example.homeworkbroadcastreceiver.databinding.ActivityTestBinding
import kotlinx.android.synthetic.main.activity_test.*
import kotlin.math.roundToInt

class TestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestBinding
    private lateinit var serviceIntent: Intent
    private var time = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // регистрируем broadcastReceiver
        serviceIntent = Intent(applicationContext, CheckTime::class.java)
        registerReceiver(br, IntentFilter(CheckTime.TIMER_UPDATED))

        // запуск сервиса
        startTimer()

        // завершение теста
        binding.buttonFinish.setOnClickListener {
           var result = if(R.id.first == binding.radioGroup2.checkedRadioButtonId) {  1 }
            else { 0 }

            binding.currentTime.text = getTimeStringFromDouble(0.0)
            stopService(serviceIntent)
            // переход и отправка
            val nextActivityMain = Intent(this, MainActivity::class.java)
            nextActivityMain.putExtra("result", result.toString())
            nextActivityMain.putExtra("time", getTimeStringFromDouble(time).toString())
            startActivity(nextActivityMain)
        }
    }

    private fun startTimer() {
        serviceIntent.putExtra(CheckTime.TIME_EXTRA, time)
        startService(serviceIntent)
    }

    private val br: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            time = intent.getDoubleExtra(CheckTime.TIME_EXTRA, 0.0)
            binding.currentTime.text = getTimeStringFromDouble(time)
        }
    }

    fun getTimeStringFromDouble(time: Double): String {
        val resultInt = time.roundToInt()
        val hours = resultInt % 86400 / 3600
        val minutes = resultInt % 86400 % 3600 / 60
        val seconds = resultInt % 86400 % 3600 % 60

        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
    
    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, CheckTime::class.java))
        unregisterReceiver(br)
    }
}