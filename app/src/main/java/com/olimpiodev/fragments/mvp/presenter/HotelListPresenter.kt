package com.olimpiodev.fragments.mvp.presenter

import com.olimpiodev.fragments.constracts.HotelRepository
import com.olimpiodev.fragments.model.Hotel
import com.olimpiodev.fragments.mvp.view.HotelListView

class HotelListPresenter(
    private val view: HotelListView,
    private val repository: HotelRepository
) {
    fun searchHotels(term: String) {
        repository.search(term) { hotels ->
            view.showHotels(hotels)
        }
    }

    fun showHotelDetails(hotel: Hotel) {
        view.showHotelDetail(hotel)
    }
}