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
import com.pzbproduction.ballerzfootball.databinding.FragmentLineUpBinding
import com.pzbproduction.ballerzfootball.model.ApiTokens
import com.pzbproduction.ballerzfootball.view.adapter.LineUpAdapter
import com.pzbproduction.ballerzfootball.viewmodel.MatchDetailsActivityViewModel


class LineUpFragment : Fragment() {
    lateinit var binding: FragmentLineUpBinding
    lateinit var viewModel: MatchDetailsActivityViewModel
    lateinit var adapter: LineUpAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLineUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[MatchDetailsActivityViewModel::class.java]
        val intent = activity?.intent
        val matchId = intent?.getStringExtra("matchId")

        val map: HashMap<String, String> = HashMap()
        map["api_token"] = ApiTokens.FIXTURES_API
        map["include"] = "lineups"

        viewModel.getLineUps(matchId!!, map)

        viewModel.getApiDataClass.observe(viewLifecycleOwner, Observer {
            adapter.getTheLineUp(it)
        })

        binding.lineUpRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = LineUpAdapter()
        binding.lineUpRecyclerView.adapter = adapter


    }


}