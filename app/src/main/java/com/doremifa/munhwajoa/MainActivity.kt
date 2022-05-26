package com.doremifa.munhwajoa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.doremifa.munhwajoa.database.Event
import com.doremifa.munhwajoa.database.EventViewModel
import com.doremifa.munhwajoa.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.parser.Parser

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val textArray = arrayListOf("홈", "즐겨찾기")
    private val iconArray =
        arrayListOf(R.drawable.ic_baseline_home_24, R.drawable.ic_baseline_star_24)

    private val url =
        "http://openapi.seoul.go.kr:8088/68524e4956776566383457446a7470/xml/culturalEventInfo/1/500"

    private lateinit var eventViewModel: EventViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        eventViewModel = ViewModelProvider(
            this,
            EventViewModel.Factory(application)
        )[EventViewModel::class.java]

        initLayout()
        read()

//        readEvent()

    }

    private fun initLayout() {
        binding.viewPager.adapter = ViewPagerAdapter(this)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = textArray[position]
            tab.setIcon(iconArray[position])
        }.attach()
    }

    private fun read() {
        CoroutineScope(Dispatchers.IO).launch {
            val doc = Jsoup.connect(url).parser(Parser.xmlParser()).get()
            val row = doc.select("row")

            for (events in row) {
                var codeName = events.select("CODENAME").text()
                var guName = events.select("GUNAME").text()
                var title = events.select("TITLE").text()
                var date = events.select("DATE").text()
                var place = events.select("PLACE").text()
                var target = events.select("USE_TRGT").text()
                var fee = events.select("USE_FEE").text()
                var player = events.select("PLAYER").text()
                var program = events.select("PROGRAM").text()
                var link = events.select("ORG_LINK").text()
                var image = events.select("MAIN_IMG").text()
                var startDate = events.select("STRTDATE").text()

                var event = Event(
                    title,
                    codeName,
                    guName,
                    date,
                    place,
                    target,
                    fee,
                    player,
                    program,
                    link,
                    image,
                    startDate
                )

                eventViewModel.insertEvent(event)
            }

        }
    }

}