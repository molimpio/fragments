package com.olimpiodev.fragments.mvp.view

import com.olimpiodev.fragments.model.Hotel

interface HotelFormView {
    fun showHotel(hotel: Hotel)
    fun errorInvalidHotel()
    fun errorSaveHotel()
}