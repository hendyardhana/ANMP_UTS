package com.example.a160420138_utsanmp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.a160420138_utsanmp.model.Booking
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DetailHistoryViewModel(application: Application):AndroidViewModel(application) {
    val detailHistoryLD = MutableLiveData<Booking>()
    private var queue: RequestQueue? = null
    private val tags = "DetaiHistorylTag"

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(tags)
    }

    fun getData(idBooking:String){
        queue = Volley.newRequestQueue(getApplication())
        val url = "https://hendyardhana.000webhostapp.com/anmp/booking.php?idbooking=$idBooking"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<Booking>() {}.type
                val result = Gson().fromJson<Booking>(it, sType)

                Log.d("Detail History Data", result.toString())
                detailHistoryLD.value = result
            },
            {
                Log.e("Detail History Data", it.toString())
            }
        )
        stringRequest.tag = tags
        queue?.add(stringRequest)
    }
}