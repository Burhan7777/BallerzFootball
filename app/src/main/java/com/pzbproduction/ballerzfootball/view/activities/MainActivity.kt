package com.pzbproduction.ballerzfootball.view.activities

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    }
}