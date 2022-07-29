package com.assignment.acronym.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.assignment.acronym.R
import com.assignment.acronym.ui.fragment.homefragment.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigateToLanding()
    }

    private fun navigateToLanding() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainContainer, HomeFragment.newInstance())
            .commit()
    }
}