package com.doremifa.munhwajoa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.doremifa.munhwajoa.database.Event
import com.doremifa.munhwajoa.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var event: Event

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        event = intent.getSerializableExtra("event") as Event
        showEventDetail()
    }

    private fun showEventDetail() {
        binding.apply {
            favoriteToggle.isChecked = event.favorite
            title.text = event.title
        }
    }
}