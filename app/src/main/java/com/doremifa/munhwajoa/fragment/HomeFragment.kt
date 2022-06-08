package com.doremifa.munhwajoa.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.doremifa.munhwajoa.R
import com.doremifa.munhwajoa.activity.DetailActivity
import com.doremifa.munhwajoa.activity.GroupActivity
import com.doremifa.munhwajoa.database.Event
import com.doremifa.munhwajoa.database.EventViewModel
import com.doremifa.munhwajoa.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.security.SecureRandom

class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null
    private var select: String = ""
    private lateinit var eventViewModel: EventViewModel
    private var data = ArrayList<Event>()
    private var bannerEvent: Event? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        eventViewModel = ViewModelProvider(
            this,
            EventViewModel.Factory(requireActivity().application)
        )[EventViewModel::class.java]

        randomEvent()
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonListener()
        randomBannerClick()
    }

    private fun randomEvent() {
        MainScope().launch {
            var random = SecureRandom()
            var favorite: ArrayList<Event> = arrayListOf()
            var events: ArrayList<Event> = arrayListOf()
            data.clear()

            withContext(Dispatchers.IO) {
                favorite.addAll(eventViewModel.readFavorite())

                if (favorite.isNotEmpty()) {
                    data.addAll(eventViewModel.readEventByCodeName(favorite[random.nextInt(favorite.size)].codeName))
                } else {
                    events.addAll(eventViewModel.readAllEvent())
                }
            }

            if (favorite.isEmpty() && events.isNotEmpty()) {
                var randomNum = random.nextInt(events.size)
                bannerEvent = events[randomNum].copy()
            } else if (favorite.isNotEmpty()) {
                var randomNum = random.nextInt(data.size)
                bannerEvent = data[randomNum].copy()
            }

            if (bannerEvent != null) {
                binding!!.apply {
                    title.text = bannerEvent!!.title
                    date.text = bannerEvent!!.date
                    place.text = bannerEvent!!.place
                    Glide.with(requireContext())
                        .load(bannerEvent!!.image)
                        .override(400, 400)
                        .fitCenter()
                        .error(R.drawable.error_img)
                        .fallback(R.drawable.error_img)
                        .into(imageView)
                }
            }

        }
    }


    private fun buttonListener() {
        binding!!.apply {
            entire.setOnClickListener {
                select = entire.text.toString()
                val intent = Intent(activity, GroupActivity::class.java)
                intent.putExtra("codeName", select)
                startActivity(intent)
            }

            classic.setOnClickListener {
                select = classic.text.toString()
                val intent = Intent(activity, GroupActivity::class.java)
                intent.putExtra("codeName", select)
                startActivity(intent)
            }

            concert.setOnClickListener {
                select = concert.text.toString()
                val intent = Intent(activity, GroupActivity::class.java)
                intent.putExtra("codeName", select)
                startActivity(intent)
            }

            musical.setOnClickListener {
                select = musical.text.toString()
                val intent = Intent(activity, GroupActivity::class.java)
                intent.putExtra("codeName", select)
                startActivity(intent)
            }

            dance.setOnClickListener {
                select = dance.text.toString()
                val intent = Intent(activity, GroupActivity::class.java)
                intent.putExtra("codeName", select)
                startActivity(intent)
            }

            exhibition.setOnClickListener {
                select = exhibition.text.toString()
                val intent = Intent(activity, GroupActivity::class.java)
                intent.putExtra("codeName", select)
                startActivity(intent)
            }

            lecture.setOnClickListener {
                select = lecture.text.toString()
                val intent = Intent(activity, GroupActivity::class.java)
                intent.putExtra("codeName", select)
                startActivity(intent)
            }

            play.setOnClickListener {
                select = play.text.toString()
                val intent = Intent(activity, GroupActivity::class.java)
                intent.putExtra("codeName", select)
                startActivity(intent)
            }

            etc.setOnClickListener {
                select = etc.text.toString()
                val intent = Intent(activity, GroupActivity::class.java)
                intent.putExtra("codeName", select)
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        randomEvent()
    }

    private fun randomBannerClick() {
        binding!!.banner.setOnClickListener {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra("event", bannerEvent)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}
