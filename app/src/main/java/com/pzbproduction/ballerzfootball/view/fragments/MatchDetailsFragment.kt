package com.pzbproduction.ballerzfootball.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pzbproduction.ballerzfootball.R
import com.pzbproduction.ballerzfootball.databinding.FragmentMatchDetailsBinding


class MatchDetailsFragment : Fragment() {
    lateinit var binding: FragmentMatchDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMatchDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

}