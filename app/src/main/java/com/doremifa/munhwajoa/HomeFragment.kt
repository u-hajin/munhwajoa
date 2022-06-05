package com.doremifa.munhwajoa

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
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
    private lateinit var bannerEvent: Event

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
                events.addAll(eventViewModel.readAllEvent())
                if (favorite.isNotEmpty()) {
                    data.addAll(eventViewModel.readEventByCodeName(favorite[random.nextInt(favorite.size)].codeName))
                }
            }

            if (favorite.isEmpty() && events.isNotEmpty()) {

                //generate a random number between 0 to size of all events
                var randomNum = random.nextInt(events.size)
                bannerEvent = events[randomNum].copy()

                binding!!.apply {
                    title.text = events[randomNum].title
                    date.text = events[randomNum].date
                    place.text = events[randomNum].place
                    Glide.with(requireContext())
                        .load(events[randomNum].image)
                        .override(400, 400)
                        .fitCenter()
                        .into(imageView)
                }
            } else if (favorite.isNotEmpty()) {
                var randomNum = random.nextInt(data.size)
                bannerEvent = data[randomNum].copy()

                binding!!.apply {
                    title.text = data[randomNum].title
                    date.text = data[randomNum].date
                    place.text = data[randomNum].place
                    Glide.with(requireContext())
                        .load(data[randomNum].image)
                        .override(400, 400)
                        .fitCenter()
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
