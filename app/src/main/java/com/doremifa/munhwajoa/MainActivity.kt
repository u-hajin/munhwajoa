package com.doremifa.munhwajoa

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.doremifa.munhwajoa.database.Event
import com.doremifa.munhwajoa.database.EventViewModel
import com.doremifa.munhwajoa.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import org.jsoup.parser.Parser
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val textArray = arrayListOf("홈", "즐겨찾기")
    private val iconArray =
        arrayListOf(R.drawable.ic_baseline_home_24, R.drawable.ic_baseline_star_24)

    private val url =
        "http://openapi.seoul.go.kr:8088/68524e4956776566383457446a7470/xml/culturalEventInfo/1/500"

    private lateinit var eventViewModel: EventViewModel

    private var favoriteList: ArrayList<Event> = arrayListOf()
    private var alarmText: String = "내일 있을 행사로는\n"
    private var message = ""

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

        setMessage()
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

//    private fun checkFavoriteDay() {
//        var calendar = Calendar.getInstance()   // 현재 시스템 상 달력
//        calendar.add(Calendar.DAY_OF_YEAR, 1)  // 현재 날짜로부터 내일 날짜
//
//        var D_Day = calendar.time
//        var formatter = SimpleDateFormat("yyyy-MM-dd") //날짜의 모양을 원하는 대로 변경 해 준다.
//        //formatter.timeZone = TimeZone.getTimeZone("Asia/Seoul")
//        var D_DayText = formatter.format(D_Day)
//
//        var num = 0
//
//        // 날짜가 정확히 하루 전날인지 확인할 기능 추가해야함
//        MainScope().launch {
//            withContext(Dispatchers.IO) {
//                favoriteList = eventViewModel.readFavorite()
//            }
//            if (favoriteList.isNotEmpty()) {
//                // 즐겨찾기가 하나라도 있을 시 기능 수행
//                for (event in favoriteList) {
//                    if (D_DayText.equals(event.date.split("~")[0])) {
//                        alarmText += event.title + "\n"
//                        num++
//                    }
//                } // 현재 날짜와 동일할 경우, 알람 문자열에 추가
//            }
////            alarm_D_Day()
//        }
//    }

//    private fun alarm_D_Day() {
//        if (favoriteList.isNotEmpty()) {
//            Toast.makeText(
//                this,
//                alarmText + "등 입니다 :)",
//                Toast.LENGTH_SHORT
//            ).show()
//        } else {
//            Toast.makeText(this, "내일은 행사가 없습니다", Toast.LENGTH_SHORT).show()
//        }
//    }

    private fun setMessage() {
        val date = Calendar.getInstance()
        date.add(Calendar.DAY_OF_YEAR, 1)

        var timeToDate = date.time
        var tomorrow = SimpleDateFormat("yyyy-MM-dd").format(timeToDate)
        var flag = false

        message += "즐겨찾기에 등록한 행사 중\n\n"

        MainScope().launch {
            withContext(Dispatchers.IO) {
                favoriteList.clear()
                favoriteList.addAll(eventViewModel.readFavorite())
            }

            if (favoriteList.isNotEmpty()) {
                for (event in favoriteList) {
                    if (event.startDate.contains(tomorrow)) {
                        message += event.title + " - " + event.place + "\n"
                        flag = true
                    }
                }

                if (flag) {
                    message += "\n" + favoriteList.size.toString() + "개의 행사가 내일부터 시작됩니다!\n"
                    setNotification()
                }
            }
        }

    }


    private fun setNotification() {

        val id = "NotifyEventStartDay"
        val name = "문화조아"
        val notificationChannel =
            NotificationChannel(id, name, NotificationManager.IMPORTANCE_DEFAULT)
        notificationChannel.enableVibration(true)
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.BLUE
        notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE

        val builder = NotificationCompat.Builder(this, id)
            .setSmallIcon(R.drawable.ic_outline_access_time_24)
            .setContentTitle("행사 알림!")
            .setContentText(message)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))

        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("event", favoriteList[0])
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP

        val pendingIntent =
            PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        builder.setContentIntent(pendingIntent)

        val notification = builder.build()
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        manager.createNotificationChannel(notificationChannel)
        manager.notify(10, notification)

    }

}