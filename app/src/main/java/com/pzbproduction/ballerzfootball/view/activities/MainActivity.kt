package com.pzbproduction.ballerzfootball.view.activities

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionInflater
import com.pzbproduction.ballerzfootball.R
import com.pzbproduction.ballerzfootball.databinding.ActivityMainBinding
import com.pzbproduction.ballerzfootball.databinding.FragmentFixturesBinding
import com.pzbproduction.ballerzfootball.view.fragments.FixturesFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().apply {
            add(binding.fixtureFragmentContainer.id, FixturesFragment::class.java, null)
            setReorderingAllowed(true)
        }.commit()

        var transitionInflater = TransitionInflater.from(this)
        var transition = transitionInflater.inflateTransition(R.transition.shared_transition)
        window.sharedElementExitTransition = transition

    }
}