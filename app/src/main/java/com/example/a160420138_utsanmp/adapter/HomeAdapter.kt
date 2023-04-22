package com.example.a160420138_utsanmp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.a160420138_utsanmp.R
import com.example.a160420138_utsanmp.model.Dokter
import com.example.a160420138_utsanmp.view.HomeFragmentDirections
import com.squareup.picasso.Picasso
import java.time.LocalTime

class HomeAdapter(private val doctorList:ArrayList<Dokter>):RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    class HomeViewHolder(var view:View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.list_doctor_layout_home,parent,false)

        return HomeViewHolder(v)
    }

    override fun getItemCount(): Int {
        return doctorList.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        with(holder.view){
            findViewById<TextView>(R.id.txtNameHome).text = "Nama : " + doctorList[position].name
            findViewById<TextView>(R.id.txtSpecialistHome).text = "Specialist: " + doctorList[position].specialist
            Picasso.get().load(doctorList[position].photo).resize(150,150).into(findViewById<ImageView>(R.id.imageViewHome))

            if(LocalTime.parse(doctorList[position].open) < LocalTime.now() && LocalTime.parse(doctorList[position].close) > LocalTime.now()){
                findViewById<TextView>(R.id.txtScheduleHome).text = "Status: Open!"
                findViewById<TextView>(R.id.txtScheduleHome).setTextColor(Color.rgb(0,255,0))
            }
            else{
                findViewById<TextView>(R.id.txtScheduleHome).text = "Status: Closed!"
                findViewById<TextView>(R.id.txtScheduleHome).setTextColor(Color.rgb(255,0,0))
            }

            findViewById<Button>(R.id.btnDetailHome).setOnClickListener {
                val action = HomeFragmentDirections.actionItemHomeToDetailFragment(doctorList[position].idDokter)
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    fun updateDoctorList(newDoctorList:ArrayList<Dokter>){
        doctorList.clear()
        doctorList.addAll(newDoctorList)
        notifyDataSetChanged()
    }
}