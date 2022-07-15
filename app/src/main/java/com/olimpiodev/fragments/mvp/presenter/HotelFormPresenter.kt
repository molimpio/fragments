package com.olimpiodev.fragments.mvp.presenter

import com.olimpiodev.fragments.HotelValidator
import com.olimpiodev.fragments.constracts.HotelRepository
import com.olimpiodev.fragments.model.Hotel
import com.olimpiodev.fragments.mvp.view.HotelFormView
import java.lang.Exception

class HotelFormPresenter(
    private val view: HotelFormView,
    private val repository: HotelRepository
) {
    private val validator = HotelValidator()

    fun loadHotel(id: Long) {
        repository.hotelById(id) { hotel ->
            if (hotel != null) {
                view.showHotel(hotel)
            }
        }
    }

    fun saveHotel(hotel: Hotel) : Boolean {
        return if (validator.validate(hotel)) {
            try {
                repository.save(hotel)
                true
            } catch (e: Exception) {
                view.errorSaveHotel()
                false
            }
        } else {
            view.errorInvalidHotel()
            false
        }
    }
}