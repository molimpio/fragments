package com.olimpiodev.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.olimpiodev.fragments.model.Hotel

class HotelActivity : AppCompatActivity(), HotelListFragment.OnHotelClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel)
    }

    override fun onHotelClick(hotel: Hotel) {
        showDetailsActivity(hotel.id)
    }

    private fun showDetailsActivity(hotelId: Long) {
        HotelDetailsActivity.open(this, hotelId)
    }
}