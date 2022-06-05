package com.doremifa.munhwajoa

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.doremifa.munhwajoa.database.Event
import com.doremifa.munhwajoa.database.EventViewModel
import com.doremifa.munhwajoa.databinding.ActivityGroupBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val FREE = "무료"

class GroupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGroupBinding
    private var data = ArrayList<Event>()
    private lateinit var adapter: GroupAdapter
    private var codeName: String = ""
    private lateinit var eventViewModel: EventViewModel
    private val sortList = arrayListOf("가나다순", "날짜 내림차순", "날짜 오름차순", "무료 포함")
    private lateinit var sortAdapter: ArrayAdapter<String>
    private var sorted: List<Event> = listOf()


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

        sortAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, sortList)
        binding.sortSpinner.adapter = sortAdapter
        binding.sortSpinner.setSelection(0)

        initList()
        search()
    }

    private fun initList() {

        MainScope().launch {
            withContext(Dispatchers.IO) {
                data = if (codeName == "전체") {
                    ArrayList(eventViewModel.readAllEvent())
                } else {
                    ArrayList(eventViewModel.readEventByCodeName(codeName))
                }
            }
            initRecyclerView()
            setSortSpinner()
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

            override fun favoriteToggleClick(data: Event) {
                if (!data.favorite) {
                    data.favorite = true
                    eventViewModel.addFavorite(data)
                } else {
                    data.favorite = false
                    eventViewModel.deleteFavorite(data)
                }
            }
        }

        binding.groupList.adapter = adapter
    }


    private fun search() {
        var searchViewTextListener: SearchView.OnQueryTextListener =
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(s: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(s: String?): Boolean {
                    adapter.filter.filter(s)
                    return false
                }
            }
        binding.searchView.setOnQueryTextListener(searchViewTextListener)
    }

    private fun setSortSpinner() {
        binding.sortSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if (codeName == "전체") {
                        readToSort(p2, true)
                    } else {
                        readToSort(p2)
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun readToSort(select: Int, isAll: Boolean = false) {

        MainScope().launch {
            withContext(Dispatchers.IO) {
                if (isAll) {
                    sorted = when (select) {
                        0 -> eventViewModel.readAllEvent()
                        1 -> eventViewModel.readAllEventNewest()
                        2 -> eventViewModel.readAllEventOldest()
                        3 -> eventViewModel.readAllEventFree("%$FREE%")
                        else -> eventViewModel.readAllEvent()
                    }
                } else {
                    sorted = when (select) {
                        0 -> eventViewModel.readEventByCodeName(codeName)
                        1 -> eventViewModel.readEventNewest(codeName)
                        2 -> eventViewModel.readEventOldest(codeName)
                        3 -> eventViewModel.readEventFree(codeName, "%$FREE%")
                        else -> eventViewModel.readEventByCodeName(codeName)
                    }
                }
            }
            data.clear()
            data.addAll(sorted)
            binding.groupList.adapter?.notifyDataSetChanged()
            adapter.notifyChange()
        }

    }

}