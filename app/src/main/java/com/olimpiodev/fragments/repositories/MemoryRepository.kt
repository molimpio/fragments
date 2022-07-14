package com.olimpiodev.fragments.repositories

import com.olimpiodev.fragments.constracts.HotelRepository
import com.olimpiodev.fragments.model.Hotel

object MemoryRepository : HotelRepository {

    private var nextId = 1L
    private val hotelsList = mutableListOf<Hotel>()

    init {
        save(Hotel(0, "New Beach Hotel", "Av. Boa viagem", 4.5f))
        save(Hotel(0, "Recife hotel", "Av. Boa viagem", 4.0f))
        save(Hotel(0, "Canario hotel", "Av. dos navegantes", 45.7f))
        save(Hotel(0, "Copacabana Palace", "Av. Santo antonio", 4.6f))
        save(Hotel(0, "Antonio's hotel", "Av. Independência", 341.5f))
        save(Hotel(0, "New Beach Hotel", "Av. Boa viagem", 4.5f))
        save(Hotel(0, "Recife hotel", "Av. Boa viagem", 4.0f))
        save(Hotel(0, "Canario hotel", "Av. dos navegantes", 45.7f))
        save(Hotel(0, "Copacabana Palace", "Av. Santo antonio", 4.6f))
        save(Hotel(0, "Antonio's hotel", "Av. Independência", 341.5f))
        save(Hotel(0, "New Beach Hotel", "Av. Boa viagem", 4.5f))
        save(Hotel(0, "Recife hotel", "Av. Boa viagem", 4.0f))
        save(Hotel(0, "Canario hotel", "Av. dos navegantes", 45.7f))
        save(Hotel(0, "Copacabana Palace", "Av. Santo antonio", 4.6f))
        save(Hotel(0, "Antonio's hotel", "Av. Independência", 341.5f))
    }

    override fun save(hotel: Hotel) {
        if (hotel.id == 0L) {
            hotel.id = nextId++
            hotelsList.add(hotel)
        } else {
            val index = hotelsList.indexOfFirst { it.id == hotel.id }
            if (index > -1) {
                hotelsList[index] = hotel
            } else {
                hotelsList.add(hotel)
            }
        }
    }

    override fun remove(vararg hotels: Hotel) {
        hotelsList.removeAll(hotels)
    }

    override fun hotelById(id: Long, callback: (Hotel?) -> Unit) {
        callback(hotelsList.find { it.id == id })
    }

    override fun search(term: String, callback: (List<Hotel>) -> Unit) {
        callback(
            if (term.isEmpty()) hotelsList
            else hotelsList.filter {
                it.name.uppercase().contains(term.uppercase())
            }
        )
    }
}