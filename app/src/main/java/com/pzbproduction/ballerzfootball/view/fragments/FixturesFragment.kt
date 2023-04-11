package com.pzbproduction.ballerzfootball.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pzbproduction.ballerzfootball.R
import com.pzbproduction.ballerzfootball.databinding.FragmentFixturesBinding
import com.pzbproduction.ballerzfootball.model.ApiDataClass
import com.pzbproduction.ballerzfootball.model.ApiTokens
import com.pzbproduction.ballerzfootball.view.adapter.FixturesAdapter
import com.pzbproduction.ballerzfootball.viewmodel.FixturesFragmentViewModel

class FixturesFragment : Fragment() {
    private lateinit var binding: FragmentFixturesBinding
    private lateinit var adapter: FixturesAdapter
    private lateinit var viewModel: FixturesFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFixturesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var map: HashMap<String, String> = HashMap()
        map["api_token"] = ApiTokens.FIXTURES_API
        map["filters"] = "todayDate"
        map["include"] = "participants"

        viewModel = ViewModelProvider(this)[FixturesFragmentViewModel::class.java]
        viewModel.getTodaysMatch(map)
        viewModel.getLogosOfHomeClub()
        viewModel.getLogosOfAwayClub()

        viewModel.getApiDataClass.observe(viewLifecycleOwner, Observer {
            adapter.updateList(it)
            Log.i("sizeFragment", it.toString())
        })

        viewModel.getLogosOfHomeClubs.observe(viewLifecycleOwner, Observer {
            adapter.getLogosOfHomeClubs(it)
        })

        viewModel.getLogosOfAwayClubs.observe(viewLifecycleOwner, Observer {
            adapter.getLogosOfAwayClubs(it)
        })


        binding.fixturesRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = FixturesAdapter(requireContext())
        binding.fixturesRecyclerView.adapter = adapter

        binding.fixtureProgressBar.visibility = View.INVISIBLE
    }
}