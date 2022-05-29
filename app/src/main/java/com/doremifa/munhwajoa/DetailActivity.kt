package com.doremifa.munhwajoa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
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
            Glide.with(eventImage)
                .load(event.image)
                .override(400,400)
                .fitCenter()
                .into(eventImage)
            title.text = event.title
            date.text = event.date
            place.text = event.place
            target.text = event.target
            fee.text = event.fee
            player.text = event.player
            program.text = event.program
        }
    }
}