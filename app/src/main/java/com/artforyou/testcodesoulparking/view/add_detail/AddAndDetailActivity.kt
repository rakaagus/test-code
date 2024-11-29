package com.artforyou.testcodesoulparking.view.add_detail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.artforyou.testcodesoulparking.R
import com.artforyou.testcodesoulparking.databinding.ActivityAddAndDetailBinding

class AddAndDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddAndDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddAndDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}