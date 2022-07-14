package com.olimpiodev.fragments.mvp.view

import com.olimpiodev.fragments.model.Hotel

interface HotelListView {
    fun showHotels(hotels: List<Hotel>)
    fun showHotelDetail(hotel: Hotel)
}