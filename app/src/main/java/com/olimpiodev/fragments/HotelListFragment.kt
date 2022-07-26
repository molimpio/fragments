package com.olimpiodev.fragments

import android.os.Bundle
import androidx.appcompat.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.ListFragment
import com.olimpiodev.fragments.model.Hotel
import com.olimpiodev.fragments.mvp.presenter.HotelListPresenter
import com.olimpiodev.fragments.mvp.view.HotelListView
import com.olimpiodev.fragments.repositories.MemoryRepository

class HotelListFragment : ListFragment(),
    HotelListView,
    AdapterView.OnItemLongClickListener,
    ActionMode.Callback {

    private var actionMode: ActionMode? = null
    private val presenter = HotelListPresenter(this, MemoryRepository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.searchHotels("")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.searchHotels("")
        listView.onItemLongClickListener = this
    }

    override fun showHotels(hotels: List<Hotel>) {
        val adapter = ArrayAdapter<Hotel>(requireContext(),
            android.R.layout.simple_list_item_activated_1, hotels)
        listAdapter = adapter
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        val hotel = l.getItemAtPosition(position) as Hotel
        presenter.selectHotel(hotel)
    }

    fun search(text: String) {
        presenter.searchHotels(text)
    }

    fun clearSearch() {
        presenter.searchHotels("")
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

    override fun onItemLongClick(parent: AdapterView<*>?, p1: View?, p2: Int, p3: Long): Boolean {
        val consumed = (actionMode == null)
        if (consumed) {
            val hotel = parent?.getItemAtPosition(p2) as Hotel
            presenter.showDeleteMode()
            presenter.selectHotel(hotel)
        }
        return consumed
    }

    override fun showDeleteMode() {
        val appCompatActivity = (activity as AppCompatActivity)
        actionMode = appCompatActivity.startSupportActionMode(this)
        listView.onItemLongClickListener = null
        listView.choiceMode = ListView.CHOICE_MODE_MULTIPLE
    }

    override fun hideDeleteMode() {
        listView.onItemLongClickListener = this
        for (i in 0 until listView.count) {
            listView.setItemChecked(i, false)
        }
        listView.post {
            actionMode?.finish()
            listView.choiceMode = ListView.CHOICE_MODE_NONE
        }
    }

    override fun updateSelectionCountText(count: Int) {
        view?.post {
            actionMode?.title = resources.getQuantityString(R.plurals.list_hotel_selected,
                count, count)
        }
    }

    override fun showSelectedHotels(hotels: List<Hotel>) {
        listView.post {
            for (i in 0 until listView.count) {
                val hotel = listView.getItemAtPosition(i) as Hotel
                if (hotels.find { it.id == hotel.id } != null) {
                    listView.setItemChecked(i, true)
                }
            }
        }
    }

    override fun onActionItemClicked(p0: ActionMode?, p1: MenuItem?): Boolean {
        if (p1?.itemId == R.id.action_delete) {
            presenter.deleteSelected { hotels ->
                if (activity is OnHotelDeletedListener) {
                    (activity as OnHotelDeletedListener).onHotelsDeleted(hotels)
                }
            }
            return true
        }
        return false
    }

    override fun onCreateActionMode(p0: ActionMode?, menu: Menu?): Boolean {
        activity?.menuInflater?.inflate(R.menu.hotel_delete_list, menu)
        return true
    }

    override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean = false

    override fun onDestroyActionMode(p0: ActionMode?) {
        actionMode = null
        presenter.hideDeleteMode()
    }

    interface OnHotelDeletedListener {
        fun onHotelsDeleted(hotels: List<Hotel>)
    }
}