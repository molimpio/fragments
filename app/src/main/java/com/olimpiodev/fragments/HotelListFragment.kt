package com.olimpiodev.fragments

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.ListFragment
import com.olimpiodev.fragments.model.Hotel
import com.olimpiodev.fragments.mvp.presenter.HotelListPresenter
import com.olimpiodev.fragments.mvp.view.HotelListView
import com.olimpiodev.fragments.repositories.MemoryRepository

class HotelListFragment : ListFragment(), HotelListView {

    private val presenter = HotelListPresenter(this, MemoryRepository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.searchHotels("")
    }

    override fun showHotels(hotels: List<Hotel>) {
        val adapter = ArrayAdapter<Hotel>(requireContext(), android.R.layout.simple_list_item_1, hotels)
        listAdapter = adapter
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        val hotel = l.getItemAtPosition(position) as Hotel
        presenter.showHotelDetails(hotel)
    }

    interface OnHotelClickListener {
        fun onHotelClick(hotel: Hotel)
    }

    override fun showHotelDetail(hotel: Hotel) {
        if (activity is OnHotelClickListener) {
            val listener = activity as OnHotelClickListener
            listener.onHotelClick(hotel)
        }
    }
}