package com.doremifa.munhwajoa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.doremifa.munhwajoa.database.Event
import com.doremifa.munhwajoa.database.EventViewModel
import com.doremifa.munhwajoa.databinding.ActivityGroupBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.security.acl.Group

class GroupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGroupBinding
    private val data: ArrayList<Event> = ArrayList()
    private lateinit var adapter: GroupAdapter
    private var codeName: String = ""
    private lateinit var eventViewModel: EventViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityGroupBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        eventViewModel = ViewModelProvider(
            this,
            EventViewModel.Factory(application)
        )[EventViewModel::class.java]

        codeName = intent.getStringExtra("codeName")!!

        binding.codeName.text = codeName

        initList()
    }

    private fun initList() {
        if (codeName == "전체") {
            MainScope().launch {
                withContext(Dispatchers.IO) {
                    var events = eventViewModel.readAllEvent()

                    for (event in events) {
                        data.add(event)
                    }
                }
                initRecyclerView()
            }

        } else {
            MainScope().launch {
                withContext(Dispatchers.IO) {
                    var events = eventViewModel.readEventByCodeName(codeName)

                    for (event in events) {
                        data.add(event)
                    }
                }
                initRecyclerView()
            }

        }
    }

    private fun initRecyclerView() {
        binding.groupList.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )

        adapter = GroupAdapter(data)
        adapter.itemClickListener = object : GroupAdapter.OnItemClickListener {
            override fun OnItemClick(data: Event) {
                val intent = Intent(applicationContext, DetailActivity::class.java)
                intent.putExtra("event", data)
                startActivity(intent)
            }
        }

        binding.groupList.adapter = adapter
    }
}