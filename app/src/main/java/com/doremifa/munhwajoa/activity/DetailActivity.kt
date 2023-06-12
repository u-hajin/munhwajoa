package com.doremifa.munhwajoa.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.doremifa.munhwajoa.database.Event
import com.doremifa.munhwajoa.database.EventViewModel
import com.doremifa.munhwajoa.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var event: Event
    private lateinit var eventViewModel: EventViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        eventViewModel = ViewModelProvider(
            this,
            EventViewModel.Factory(application)
        )[EventViewModel::class.java]

        event = intent.getSerializableExtra("event") as Event
        showEventDetail()
        initLayout()
        favoriteToggleClick()
    }

    private fun initLayout() {
        binding.apply {
            linkButton.setOnClickListener {
                showHomePage()
            }
            mapButton.setOnClickListener {
                showMap()
            }
        }
    }

    private fun showMap() {
        val mapStr = "geo:0,0?q=" + event.place
        val mapPage = Uri.parse(mapStr)
        val mapIntent = Intent(Intent.ACTION_VIEW, mapPage)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    private fun showHomePage() {
        val webpage = Uri.parse(event.link)
        val webIntent = Intent(Intent.ACTION_VIEW, webpage)
        startActivity(webIntent)
    }

    private fun showEventDetail() {
        binding.apply {
            favoriteToggle.isChecked = event.favorite

            Glide.with(applicationContext)
                .load(event.image)
                .override(300, 300)
                .fitCenter()
                .into(eventImage)

            title.text = event.title
            date.text = event.date
            place.text = event.place
            target.text = if (event.target == "") "-" else event.target
            fee.text = if (event.fee == "") "-" else event.fee
            player.text = if (event.player == "") "-" else event.player
            program.text = if (event.program == "") "-" else event.program
        }
    }

    private fun favoriteToggleClick() {
        binding.favoriteToggle.setOnClickListener {
            event.favorite = !event.favorite
            eventViewModel.updateFavorite(event)
        }
    }
}
