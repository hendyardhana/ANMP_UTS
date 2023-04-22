package com.example.a160420138_utsanmp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.a160420138_utsanmp.model.Dokter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SearchViewModel(application: Application):AndroidViewModel(application) {
    val searchDoctorLD = MutableLiveData<ArrayList<Dokter>>()
    val searchLoadError = MutableLiveData<Boolean>()
    val searchLoading = MutableLiveData<Boolean>()
    private var queue: RequestQueue? = null
    val tags = "SearchTag"

    fun getData(search:String){
        var tempData = ArrayList<Dokter>()
        var usedData = ArrayList<Dokter>()

        searchLoadError.value = false
        searchLoading.value = true

        queue = Volley.newRequestQueue(getApplication())
        val url = "https://hendyardhana.000webhostapp.com/anmp/listdokter.php"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<List<Dokter>>() {}.type
                val result = Gson().fromJson<List<Dokter>>(it, sType)
                tempData = result as ArrayList<Dokter>

                if(search != ""){
                    for (i in 0 until tempData.size){
                        if(tempData[i].name.toUpperCase().contains(search.toUpperCase())){
                            usedData.add(tempData[i])
                        }
                    }
                }
                else{
                    usedData = tempData
                }

                searchDoctorLD.value = usedData
                searchLoading.value = false
                Log.d("Search Data", result.toString())
            },
            {
                searchLoading.value = false
                searchLoadError.value = true
                Log.e("Search Data", it.toString())
            }
        )
        stringRequest.tag = tags
        queue?.add(stringRequest)
    }
}