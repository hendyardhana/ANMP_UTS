package com.example.a160420138_utsanmp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.a160420138_utsanmp.R
import com.example.a160420138_utsanmp.adapter.HomeAdapter
import com.example.a160420138_utsanmp.adapter.SearchAdapter
import com.example.a160420138_utsanmp.viewmodel.HomeViewModel
import com.example.a160420138_utsanmp.viewmodel.SearchViewModel

class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchViewModel
    private val doctorListAdapter = SearchAdapter(arrayListOf())
    var search = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        viewModel.getData(search)
        view.findViewById<RecyclerView>(R.id.recViewSearchFragment).layoutManager = LinearLayoutManager(context)
        view.findViewById<RecyclerView>(R.id.recViewSearchFragment).adapter = doctorListAdapter
        observeViewModel()

        view.findViewById<EditText>(R.id.editTextSearch).doOnTextChanged { text, start, before, count ->
            search = text.toString()
            viewModel.getData(search)
            observeViewModel()
        }

        view.findViewById<SwipeRefreshLayout>(R.id.refreshLayoutSearchFragment).setOnRefreshListener {
            view.findViewById<RecyclerView>(R.id.recViewSearchFragment).visibility = View.GONE
            view.findViewById<TextView>(R.id.txtErrorSearchFragment).visibility = View.GONE
            view.findViewById<ProgressBar>(R.id.progressBarSearchFragment).visibility = View.VISIBLE
            viewModel.getData(search)
            view.findViewById<SwipeRefreshLayout>(R.id.refreshLayoutSearchFragment).isRefreshing = false
        }
    }

    private fun observeViewModel() {
        viewModel.searchDoctorLD.observe(viewLifecycleOwner, Observer {
            doctorListAdapter.updateDoctorList(it)
        })

        viewModel.searchLoading.observe(viewLifecycleOwner, Observer {
            val loading = view?.findViewById<ProgressBar>(R.id.progressBarSearchFragment)
            if(it == true){
                loading?.visibility = View.VISIBLE
                view?.findViewById<RecyclerView>(R.id.recViewSearchFragment)?.visibility = View.GONE
            }else{
                loading?.visibility = View.GONE
                view?.findViewById<RecyclerView>(R.id.recViewSearchFragment)?.visibility = View.VISIBLE
            }
        })

        viewModel.searchLoadError.observe(viewLifecycleOwner, Observer {
            val error = view?.findViewById<TextView>(R.id.txtErrorSearchFragment)
            if(it == true){
                error?.visibility = View.VISIBLE
            }else{
                error?.visibility = View.GONE
            }
        })
    }
}