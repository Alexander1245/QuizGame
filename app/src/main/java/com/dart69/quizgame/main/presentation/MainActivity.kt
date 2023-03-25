package com.dart69.quizgame.main.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dart69.quizgame.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}