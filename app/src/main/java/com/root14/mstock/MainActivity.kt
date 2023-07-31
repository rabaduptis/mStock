package com.root14.mstock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.root14.mstock.databinding.ActivityMainBinding
import com.root14.mstock.databinding.FragmentLoginGoogleBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}