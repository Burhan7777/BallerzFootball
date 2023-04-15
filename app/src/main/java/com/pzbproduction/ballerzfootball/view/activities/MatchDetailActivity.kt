package com.pzbproduction.ballerzfootball.view.activities

import android.graphics.Bitmap
import android.graphics.Interpolator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.tabs.TabLayout
import com.pzbproduction.ballerzfootball.databinding.ActivityMainBinding
import com.pzbproduction.ballerzfootball.databinding.ActivityMatchDetailBinding
import com.pzbproduction.ballerzfootball.view.fragments.LineUpFragment
import com.pzbproduction.ballerzfootball.view.fragments.StatisticsFragment
import com.pzbproduction.ballerzfootball.viewmodel.MatchDetailsActivityViewModel
import kotlin.math.hypot

class MatchDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityMatchDetailBinding
    lateinit var viewModel: MatchDetailsActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MatchDetailsActivityViewModel::class.java]

        var intent = intent
        var homeLogo: Bitmap = intent.getParcelableExtra("homeLogo")!!
        var awayLogo: Bitmap = intent.getParcelableExtra("awayLogo")!!
        var homeTeam = intent.getStringExtra("homeTeam")
        var awayTeam = intent.getStringExtra("awayTeam")

        Glide.with(this).load(homeLogo).centerInside()
            .diskCacheStrategy(DiskCacheStrategy.ALL).into(binding.DetailMatchHomeTeamImage)

        Glide.with(this).load(awayLogo).centerInside()
            .diskCacheStrategy(DiskCacheStrategy.ALL).into(binding.DetailMatchAwayTeamImage)

        binding.DetailMatchHomeTeamName.text = homeTeam
        binding.DetailMatchAwayTeamName.text = awayTeam

        binding.DetailMatchCardView.postDelayed(Runnable {
            createRevealAnimationOnCardView()
        }, 100)


        binding.matchDetailTabLayout.addTab(
            binding.matchDetailTabLayout.newTab().setText("Line Up")
        )
        binding.matchDetailTabLayout.addTab(
            binding.matchDetailTabLayout.newTab().setText("Statistics")
        )
        binding.matchDetailViewPager.adapter = MyFragmentAdapter(supportFragmentManager, lifecycle)

        binding.matchDetailTabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.matchDetailViewPager.currentItem =
                    binding.matchDetailTabLayout.selectedTabPosition
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        binding.matchDetailViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.matchDetailTabLayout.selectTab(
                    binding.matchDetailTabLayout.getTabAt(
                        position
                    )
                )
            }

        })

    }


    private fun createRevealAnimationOnCardView() {

        var cx = binding.DetailMatchCardView.width / 2
        var cy = binding.DetailMatchCardView.height / 2
        var finalRadius =
            hypot(
                binding.DetailMatchCardView.width.toDouble(),
                binding.DetailMatchCardView.height.toDouble()
            )
        var animator = ViewAnimationUtils.createCircularReveal(
            binding.DetailMatchCardView, cx, cy, 0F, finalRadius.toFloat()
        )

        animator.duration = 2000
        animator.interpolator = DecelerateInterpolator()
        binding.DetailMatchCardView.visibility = View.VISIBLE
        animator.start()
    }

    class MyFragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
        FragmentStateAdapter(fragmentManager, lifecycle) {
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            var fragment = Fragment()
            if (position == 0)
                fragment = LineUpFragment()
            if (position == 1)
                fragment = StatisticsFragment()

            return fragment
        }

    }

}
