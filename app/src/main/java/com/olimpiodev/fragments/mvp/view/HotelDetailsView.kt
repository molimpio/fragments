package com.olimpiodev.fragments.mvp.view

import com.olimpiodev.fragments.model.Hotel

interface HotelDetailsView {
    fun showHotelDetails(hotel: Hotel)
    fun errorHotelNotFound()
}