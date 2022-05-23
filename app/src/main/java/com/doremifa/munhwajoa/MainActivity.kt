package com.doremifa.munhwajoa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.doremifa.munhwajoa.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val textArray = arrayListOf("홈", "즐겨찾기")
    val iconArray = arrayListOf(R.drawable.ic_baseline_home_24, R.drawable.ic_baseline_star_24)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    private fun initLayout() {
        binding.viewPager.adapter = ViewPagerAdapter(this)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = textArray[position]
            tab.setIcon(iconArray[position])
        }.attach()
    }

    private fun read(a: String) {

    }
}