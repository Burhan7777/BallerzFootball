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
import com.pzbproduction.ballerzfootball.databinding.FragmentFixturesBinding
import com.pzbproduction.ballerzfootball.model.ApiTokens
import com.pzbproduction.ballerzfootball.view.adapter.FixturesAdapter
import com.pzbproduction.ballerzfootball.viewmodel.FixturesFragmentViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

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
        //  viewModel.getIsLoading()
        //  viewModel.getHasLoaded()

        //   Log.i("viewmodel", viewModel.getLogosOfHomeClubs.toString())


        viewModel.getIsLoading.observe(viewLifecycleOwner, Observer {
            if (it) binding.fixtureProgressBar.visibility = View.VISIBLE
        })

        viewModel.getHasLoaded.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.fixtureProgressBar.visibility = View.INVISIBLE
                binding.fixturesNoResult.text = "No matches today"
            }

        })

        if (viewModel.getApiDataClass.value != null) {
            viewModel.getApiDataClass.observe(viewLifecycleOwner, Observer {
                adapter.updateList(it)
                //  Log.i("sizeFragment", it.toString())
            })

            viewModel.getLogosOfHomeClubs.observe(viewLifecycleOwner, Observer {
                adapter.getLogosOfHomeClubs(it)
            })

            viewModel.getLogosOfAwayClubs.observe(viewLifecycleOwner, Observer {
                adapter.getLogosOfAwayClubs(it)
            })

            viewModel.getApiDataClassFromSpinner.observe(viewLifecycleOwner, Observer {
                adapter.updateList(it)
            })

        }

        viewModel.getMatchesBySpecificDate.observe(viewLifecycleOwner, Observer {
            adapter.updateList(it)
          //  Log.i("specific", it.size.toString())
        })

        viewModel.getLogosOfHomeClubsFromSpecificDate.observe(viewLifecycleOwner, Observer {
            adapter.getLogosOfHomeClubs(it)
        //    Log.i("specificHome", it.size.toString())
        })

        viewModel.getLogosOfAwayClubsFromSpecificDate.observe(viewLifecycleOwner, Observer {
            adapter.getLogosOfAwayClubs(it)
        //    Log.i("specificAway", it.size.toString())
        })


        binding.fixturesRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = FixturesAdapter(requireContext())
        binding.fixturesRecyclerView.adapter = adapter

        binding.fixtureProgressBar.visibility = View.INVISIBLE

        binding.fixtureAutocomplete.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                binding.fixtureName.text = parent.getItemAtPosition(0).toString()
                var mapSuperliga = HashMap<String, String>()
                mapSuperliga["api_token"] = ApiTokens.FIXTURES_API
                mapSuperliga["filters"] = "fixtureLeagues:271;todayDate"

                viewModel.getApiDataClassFromSpinner(mapSuperliga)

            }

            if (position == 1) {
                binding.fixtureName.text = parent.getItemAtPosition(1).toString()
                var mapPremiership = HashMap<String, String>()
                mapPremiership["api_token"] = ApiTokens.FIXTURES_API
                mapPremiership["filters"] = "fixtureLeagues:501;todayDate"

                viewModel.getApiDataClassFromSpinner(mapPremiership)
            }
        }

        sendApiCallsFromDifferentDates()

        binding.fixtureDateOne.text = getDateOnButtons(-3)
        binding.fixtureDateTwo.text = getDateOnButtons(-2)
        binding.fixtureDateThree.text = getDateOnButtons(-1)
        binding.fixtureDateFour.text = "TODAY"
        binding.fixtureDateFive.text = getDateOnButtons(1)
        binding.fixtureDateSix.text = getDateOnButtons(2)
        binding.fixtureDateSeven.text = getDateOnButtons(3)

    }

    private fun getDateOnButtons(amount: Int): String {
        var calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, amount)
        var dateFormat = SimpleDateFormat("dd MM")
        var date = dateFormat.format(calendar.time)
        var month = getDescriptiveMonth(date)
        return date.replace(date.substring(3, 5), month)

    }

    private fun getDescriptiveMonth(date: String): String {
        var month = when (date.substring(3, 5)) {
            "01" -> return "JAN"
            "02" -> return "FEB"
            "03" -> return "MAR"
            "04" -> return "APR"
            "05" -> return "MAY"
            "06" -> return "JUN"
            "07" -> return "JUL"
            "08" -> return "AUG"
            "09" -> return "SEP"
            "10" -> return "OCT"
            "11" -> return "NOV"
            "12" -> return "DEC"
            else -> "Error"
        }
        return month
    }

    private fun sendApiCallsFromDifferentDates() {
        binding.fixtureDateOne.setOnClickListener {
            var mapDate: HashMap<String, String> = HashMap()
            mapDate["api_token"] = ApiTokens.FIXTURES_API
            mapDate["include"] = "participants"
            var date = getProperDateFormatForTheApiCall(-3)
            viewModel.getMatchesBySpecificDate(date, date, mapDate)
            viewModel.getLogosOfHomeClubFromSpecificDate()
            viewModel.getLogosOfAwayClubFromSpecificDate()

        }

        binding.fixtureDateTwo.setOnClickListener {
            var mapDate: HashMap<String, String> = HashMap()
            mapDate["api_token"] = ApiTokens.FIXTURES_API
            mapDate["include"] = "participants"
            var date = getProperDateFormatForTheApiCall(-2)
            viewModel.getMatchesBySpecificDate(date, date, mapDate)
            viewModel.getLogosOfHomeClubFromSpecificDate()
            viewModel.getLogosOfAwayClubFromSpecificDate()

        }

        binding.fixtureDateThree.setOnClickListener {
            var mapDate: HashMap<String, String> = HashMap()
            mapDate["api_token"] = ApiTokens.FIXTURES_API
            mapDate["include"] = "participants"
            var date = getProperDateFormatForTheApiCall(-1)
            viewModel.getMatchesBySpecificDate(date, date, mapDate)
            viewModel.getLogosOfHomeClubFromSpecificDate()
            viewModel.getLogosOfAwayClubFromSpecificDate()

        }

        binding.fixtureDateFour.setOnClickListener {
            var mapDate: HashMap<String, String> = HashMap()
            mapDate["api_token"] = ApiTokens.FIXTURES_API
            mapDate["include"] = "participants"
            var date = getProperDateFormatForTheApiCall(0)
            viewModel.getMatchesBySpecificDate(date, date, mapDate)
            viewModel.getLogosOfHomeClubFromSpecificDate()
            viewModel.getLogosOfAwayClubFromSpecificDate()

        }

        binding.fixtureDateFive.setOnClickListener {
            var mapDate: HashMap<String, String> = HashMap()
            mapDate["api_token"] = ApiTokens.FIXTURES_API
            mapDate["include"] = "participants"
            var date = getProperDateFormatForTheApiCall(1)
            viewModel.getMatchesBySpecificDate(date, date, mapDate)
            viewModel.getLogosOfHomeClubFromSpecificDate()
            viewModel.getLogosOfAwayClubFromSpecificDate()

        }

        binding.fixtureDateSix.setOnClickListener {
            var mapDate: HashMap<String, String> = HashMap()
            mapDate["api_token"] = ApiTokens.FIXTURES_API
            mapDate["include"] = "participants"
            var date = getProperDateFormatForTheApiCall(2)
            viewModel.getMatchesBySpecificDate(date, date, mapDate)
            viewModel.getLogosOfHomeClubFromSpecificDate()
            viewModel.getLogosOfAwayClubFromSpecificDate()

        }

        binding.fixtureDateSeven.setOnClickListener {
            var mapDate: HashMap<String, String> = HashMap()
            mapDate["api_token"] = ApiTokens.FIXTURES_API
            mapDate["include"] = "participants"
            var date = getProperDateFormatForTheApiCall(3)
            viewModel.getMatchesBySpecificDate(date, date, mapDate)
            viewModel.getLogosOfHomeClubFromSpecificDate()
            viewModel.getLogosOfAwayClubFromSpecificDate()

        }


    }

    private fun getProperDateFormatForTheApiCall(amount: Int): String {
        var calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, amount)
        var dateFormat = SimpleDateFormat("yyyy-MM-dd")
        var date = dateFormat.format(calendar.time)
        return date
    }
}