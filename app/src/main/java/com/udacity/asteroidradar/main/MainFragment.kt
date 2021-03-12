package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.database.NeoDatabase
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: NeoAdapter

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = FragmentMainBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application
        val dataSource = NeoDatabase.getInstance(application).neoDatabaseDao
        val viewModelFactory = MainViewModelFactory(dataSource)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        adapter = NeoAdapter(createNeoClickListener())

        binding.asteroidRecycler.adapter = adapter
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.statusLoadingWheel.visibility = View.VISIBLE

        setObservers(binding)

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun createNeoClickListener() = NeoClickListener { asteroid ->
        viewModel.onAsteroidClicked(asteroid)
    }

    private fun setObservers(binding: FragmentMainBinding) {
        viewModel.imageOfTheDayResponse.observe(viewLifecycleOwner, {
            binding.statusLoadingWheel.visibility = View.GONE
        })

        viewModel.neoObjects.observe(viewLifecycleOwner, Observer { it?.let {
            viewModel.handleTriggeredNeoObjects(it)
            binding.statusLoadingWheel.visibility = View.VISIBLE
        } })

        viewModel.neoObjectsRefreshed.observe(viewLifecycleOwner, Observer { it ->
            adapter.data = it as ArrayList<Asteroid>
            binding.statusLoadingWheel.visibility = View.GONE
        })

         viewModel.navigateToDetails.observe(viewLifecycleOwner, Observer { asteroid ->
             if(viewModel.navigationAvailable){
                 findNavController().navigate(MainFragmentDirections.actionShowDetail(asteroid))
             }
         })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        binding.statusLoadingWheel.visibility = View.VISIBLE
        when (item.itemId){
            R.id.show_today_menu -> viewModel.getTodaysNeos()
            R.id.show_saved_menu -> viewModel.getSavedNeos()
            else -> viewModel.getAllNeos()
        }
        return true
    }
}
