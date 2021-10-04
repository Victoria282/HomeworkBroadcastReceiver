package com.example.homeworkbroadcastreceiver

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.homeworkbroadcastreceiver.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var res: String = ""
    var time: String = ""

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.buttonStart.setOnClickListener {
            val nextActivityTest = Intent(this, TestActivity::class.java)
            startActivity(nextActivityTest)
        }

         // данные из 2 активити (тест)
         res = intent.getStringExtra("result").toString()
         time = intent.getStringExtra("time").toString()

        if(res != "null" && time != "null") {
            binding.cardView.visibility = View.VISIBLE
            binding.timeUser.text = "Время выполнения - $time"
            binding.countUser.text = "Результат - $res"
        }
    }
}