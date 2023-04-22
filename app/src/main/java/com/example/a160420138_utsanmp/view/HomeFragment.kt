package com.example.a160420138_utsanmp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.a160420138_utsanmp.R
import com.example.a160420138_utsanmp.adapter.HomeAdapter
import com.example.a160420138_utsanmp.viewmodel.HomeViewModel
import java.time.LocalTime

class HomeFragment : Fragment() {

    private lateinit var viewModel:HomeViewModel
    private val doctorListAdapter = HomeAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.getData()
        view.findViewById<RecyclerView>(R.id.recViewHomeFragment).layoutManager = LinearLayoutManager(context)
        view.findViewById<RecyclerView>(R.id.recViewHomeFragment).adapter = doctorListAdapter
        observeViewModel()

        view.findViewById<SwipeRefreshLayout>(R.id.refreshLayoutHomeFragment).setOnRefreshListener {
            view.findViewById<RecyclerView>(R.id.recViewHomeFragment).visibility = View.GONE
            view.findViewById<TextView>(R.id.txtErrorHomeFragment).visibility = View.GONE
            view.findViewById<ProgressBar>(R.id.progressBarHomeFragment).visibility = View.VISIBLE
            viewModel.getData()
            view.findViewById<SwipeRefreshLayout>(R.id.refreshLayoutHomeFragment).isRefreshing = false
        }
    }

    private fun observeViewModel() {
        viewModel.homeDoctorLD.observe(viewLifecycleOwner, Observer {
            doctorListAdapter.updateDoctorList(it)
        })

        viewModel.homeLoading.observe(viewLifecycleOwner, Observer {
            val loading = view?.findViewById<ProgressBar>(R.id.progressBarHomeFragment)
            if(it == true){
                loading?.visibility = View.VISIBLE
                view?.findViewById<RecyclerView>(R.id.recViewHomeFragment)?.visibility = View.GONE
            }else{
                loading?.visibility = View.GONE
                view?.findViewById<RecyclerView>(R.id.recViewHomeFragment)?.visibility = View.VISIBLE
            }
        })

        viewModel.homeLoadError.observe(viewLifecycleOwner, Observer {
            val error = view?.findViewById<TextView>(R.id.txtErrorHomeFragment)
            if(it == true){
                error?.visibility = View.VISIBLE
            }else{
                error?.visibility = View.GONE
            }
        })
    }
}