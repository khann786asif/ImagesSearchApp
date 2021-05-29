package com.example.imagesearchapp.screens.overview

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.AbsListView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearchapp.R
import com.example.imagesearchapp.databinding.FragmentOverviewBinding
import com.example.imagesearchapp.network.UnsplashResponse
import kotlinx.android.synthetic.main.fragment_overview.*
import kotlin.properties.Delegates

class OverviewFragment : Fragment(R.layout.fragment_overview), OverviewAdapter.OnClickListener {


    private var binding: FragmentOverviewBinding? = null
    private val viewModel: OverviewViewModel by activityViewModels()
    lateinit var adapter: OverviewAdapter
    lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentOverviewBinding.bind(view)

        viewModel.status.observe(viewLifecycleOwner, Observer<Status> { status ->
            val progressBar = binding!!.progressBar
            val textViewError = binding!!.textViewError
            val buttonRetry = binding!!.buttonRetry

            when(status){
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    textViewError.visibility = View.GONE
                    buttonRetry.visibility = View.GONE
                }
                Status.DONE -> {
                    progressBar.visibility = View.GONE
                    textViewError.visibility = View.GONE
                    buttonRetry.visibility = View.GONE
                    viewModel.response.observe(viewLifecycleOwner, Observer {
                        adapter.submitData(it as ArrayList<UnsplashResponse.UnsplashResults>)
                        adapter.notifyDataSetChanged()
                    })
                }
                Status.ERROR -> {
                    progressBar.visibility = View.GONE
                    textViewError.visibility = View.VISIBLE
                    buttonRetry.visibility = View.VISIBLE

                    buttonRetry.setOnClickListener {
                        viewModel.retry()
                    }
                }
                else -> {
                    Toast.makeText(context, "Unexpected Error Occurred", Toast.LENGTH_LONG).show()
                }
            }
        })

        recyclerView = binding!!.recyclerView
        val layoutManager = LinearLayoutManager(context)
        adapter = OverviewAdapter(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overview_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null){
                    viewModel.changeTerm(query)
                    recyclerView.smoothScrollToPosition(0)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onClick(result: UnsplashResponse.UnsplashResults) {
       val action =  OverviewFragmentDirections.actionOverviewFragmentToDetailFragment(result)
        findNavController().navigate(action)
    }
}