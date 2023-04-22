package com.example.a160420138_utsanmp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.a160420138_utsanmp.R
import com.example.a160420138_utsanmp.viewmodel.DetailViewModel
import com.squareup.picasso.Picasso
import java.time.LocalTime

class DetailFragment : Fragment() {

    private lateinit var viewModel:DetailViewModel
    private var idDokter = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(arguments != null){
            idDokter = DetailFragmentArgs.fromBundle(requireArguments()).idDokter
        }
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.getData(idDokter)
        observeViewModel(view)

        view.findViewById<Button>(R.id.btnBookDetail).setOnClickListener {
            Toast.makeText(context, "Success Book!", Toast.LENGTH_SHORT).show()
            it.isEnabled = false
        }
    }

    private fun observeViewModel(view:View) {
        viewModel.detailDoctorLD.observe(viewLifecycleOwner, Observer {
            Picasso.get().load(it.photo).resize(150,150).into(view.findViewById<ImageView>(R.id.imageViewDetail))
            view.findViewById<TextView>(R.id.txtNameDetail).text = it.name
            view.findViewById<TextView>(R.id.txtSpecialistDetail).text = it.specialist
            view.findViewById<TextView>(R.id.txtScheduleDetail).text = "Open From " + LocalTime.parse(it.open) + " Until " + LocalTime.parse(it.close)
            view.findViewById<RatingBar>(R.id.ratingBarDetail).rating = it.rating.toFloat()
            view.findViewById<EditText>(R.id.inputDescriptionDetail).setText(it.description)
        })
    }
}