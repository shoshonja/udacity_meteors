package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.database.NeoDatabase
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application
        val dataSource = NeoDatabase.getInstance(application).neoDatabaseDao
        val viewModelFactory = MainViewModelFactory(dataSource, application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.statusLoadingWheel.visibility = View.VISIBLE

        setObservers(binding)

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun setObservers(binding: FragmentMainBinding) {
        viewModel.imageOfTheDayResponse.observe(viewLifecycleOwner, {
            binding.statusLoadingWheel.visibility = View.GONE
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
