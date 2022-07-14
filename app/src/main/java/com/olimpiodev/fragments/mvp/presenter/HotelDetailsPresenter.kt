package com.olimpiodev.fragments.mvp.presenter

import com.olimpiodev.fragments.constracts.HotelRepository
import com.olimpiodev.fragments.mvp.view.HotelDetailsView

class HotelDetailsPresenter(
    private val view: HotelDetailsView,
    private val repository: HotelRepository
) {
    fun loadHotelDetails(id: Long) {
        repository.hotelById(id) { hotel ->
            if (hotel != null) {
                view.showHotelDetails(hotel)
            } else {
                view.errorHotelNotFound()
            }
        }
    }
}