package com.olimpiodev.fragments.mvp.presenter

import com.olimpiodev.fragments.constracts.HotelRepository
import com.olimpiodev.fragments.model.Hotel
import com.olimpiodev.fragments.mvp.view.HotelListView

class HotelListPresenter(
    private val view: HotelListView,
    private val repository: HotelRepository
) {
    private var lastTerm = ""
    private var inDeleteMode = false
    private val selectedItems = mutableListOf<Hotel>()
    private val deletedItems = mutableListOf<Hotel>()

    fun searchHotels(term: String) {
        lastTerm = term
        repository.search(term) { hotels ->
            view.showHotels(hotels)
        }
    }

    fun selectHotel(hotel: Hotel) {
        if (inDeleteMode) {
            toggleHotelSelected(hotel)

            if (selectedItems.size == 0) {
                view.hideDeleteMode()
            } else {
                view.updateSelectionCountText(selectedItems.size)
                view.showSelectedHotels(selectedItems)
            }
        } else {
            view.showHotelDetail(hotel)
        }
    }

    private fun toggleHotelSelected(hotel: Hotel) {
        val existing = selectedItems.find { it.id == hotel.id }
        if (existing == null) {
            selectedItems.add(hotel)
        } else {
            selectedItems.removeAll { it.id == hotel.id }
        }
    }

    fun showDeleteMode() {
        inDeleteMode = true
        view.showDeleteMode()
    }

    fun hideDeleteMode() {
        inDeleteMode = false
        selectedItems.clear()
        view.hideDeleteMode()
    }

    fun refresh() {
        searchHotels(lastTerm)
    }

    fun deleteSelected(callback: (List<Hotel>)-> Unit) {
        repository.remove(*selectedItems.toTypedArray())
        deletedItems.clear()
        deletedItems.addAll(selectedItems)
        refresh()
        callback(selectedItems)
        hideDeleteMode()
        view.showMessageHotelsDeleted(deletedItems.size)
    }

    fun undoDelete() {
        if (deletedItems.isNotEmpty()) {
            for (hotel in deletedItems) {
                repository.save(hotel)
            }
            searchHotels(lastTerm)
        }
    }

    fun showHotelDetails(hotel: Hotel) {
        view.showHotelDetail(hotel)
    }
}