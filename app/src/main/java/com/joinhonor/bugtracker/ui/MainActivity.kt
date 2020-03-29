package com.joinhonor.bugtracker.ui

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.joinhonor.bugtracker.R
import com.joinhonor.bugtracker.util.EventObserver
import com.joinhonor.bugtracker.viewmodel.BugsViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var bugsViewModel: BugsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bugsViewModel = ViewModelProviders.of(this, viewModelFactory).get(BugsViewModel::class.java)
        bugsViewModel.getBugsList()

        bugsViewModel.showList.observe(this, EventObserver {
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, BugListFragment()).commit()
        })

        bugsViewModel.openAddBugFragment.observe(this, EventObserver{
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, AddBugFragment(), "AddBug").addToBackStack(null).commit()
        })
    }
}