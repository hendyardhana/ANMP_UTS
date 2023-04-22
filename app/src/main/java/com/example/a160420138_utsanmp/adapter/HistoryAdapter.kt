package com.example.a160420138_utsanmp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.a160420138_utsanmp.R
import com.example.a160420138_utsanmp.model.Booking
import com.example.a160420138_utsanmp.model.Dokter
import com.example.a160420138_utsanmp.view.HistoryFragmentDirections

class HistoryAdapter(private val bookingList:ArrayList<Booking>):RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    class HistoryViewHolder(var view:View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.list_booking_layout_history,parent,false)

        return HistoryViewHolder(v)
    }

    override fun getItemCount(): Int {
        return bookingList.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        with(holder.view){
            findViewById<TextView>(R.id.txtIdBookingHistory).text = bookingList[position].idBook
            findViewById<TextView>(R.id.txtNameDoctorHistory).text = bookingList[position].namaDokter
            findViewById<TextView>(R.id.txtNamePasienHistory).text = bookingList[position].namaPasien
            findViewById<TextView>(R.id.txtTanggalBookingHistory).text = bookingList[position].tanggalBooking

            findViewById<Button>(R.id.btnLihatObatHistory).setOnClickListener {
                val action = HistoryFragmentDirections.actionItemHistoryToDetailHistoryFragment(bookingList[position].idBook)
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    fun updateBookingList(newBookingList:ArrayList<Booking>){
        bookingList.clear()
        bookingList.addAll(newBookingList)
        notifyDataSetChanged()
    }
}