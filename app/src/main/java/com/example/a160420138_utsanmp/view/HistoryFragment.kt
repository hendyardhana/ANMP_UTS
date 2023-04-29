package com.example.a160420138_utsanmp.view

import android.content.Context
import android.content.SharedPreferences
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
import com.example.a160420138_utsanmp.R
import com.example.a160420138_utsanmp.adapter.HistoryAdapter
import com.example.a160420138_utsanmp.viewmodel.HistoryViewModel
import com.example.a160420138_utsanmp.viewmodel.HomeViewModel

class HistoryFragment : Fragment() {

    private lateinit var viewModel:HistoryViewModel
    private val bookingListAdapter = HistoryAdapter(arrayListOf())

    companion object{
        val sharedusername = "sharedusername"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val shared: SharedPreferences = requireActivity().getSharedPreferences(HomeFragment.sharedusername, Context.MODE_PRIVATE)
        val username = shared.getString(sharedusername, "")

        if(username == ""){
            val action = HistoryFragmentDirections.actionItemHistoryToLoginFragment()
            Navigation.findNavController(view).navigate(action)
        }
        else{
            viewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
            viewModel.getData(username!!)
            view.findViewById<RecyclerView>(R.id.recyclerViewHistoryFragment).layoutManager = LinearLayoutManager(context)
            view.findViewById<RecyclerView>(R.id.recyclerViewHistoryFragment).adapter = bookingListAdapter
            observeViewModel()
        }
    }

    private fun observeViewModel() {
        viewModel.historyLD.observe(viewLifecycleOwner, Observer {
            bookingListAdapter.updateBookingList(it)
        })

        viewModel.historyLoading.observe(viewLifecycleOwner, Observer {
            val loading = view?.findViewById<ProgressBar>(R.id.progressBarHistoryFragment)
            if(it == true){
                loading?.visibility = View.VISIBLE
                view?.findViewById<RecyclerView>(R.id.recyclerViewHistoryFragment)?.visibility = View.GONE
            }else{
                loading?.visibility = View.GONE
                view?.findViewById<RecyclerView>(R.id.recyclerViewHistoryFragment)?.visibility = View.VISIBLE
            }
        })

        viewModel.historyLoadError.observe(viewLifecycleOwner, Observer {
            val error = view?.findViewById<TextView>(R.id.txtErrorHistoryFragment)
            if(it == true){
                error?.visibility = View.VISIBLE
            }else{
                error?.visibility = View.GONE
            }
        })
    }
}