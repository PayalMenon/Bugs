package com.joinhonor.bugtracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.joinhonor.bugtracker.R
import com.joinhonor.bugtracker.viewmodel.BugsViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.add_bug.view.*
import javax.inject.Inject

class AddBugFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var bugViewModel: BugsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        bugViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory)
            .get(BugsViewModel::class.java)
        val view = inflater.inflate(R.layout.add_bug, container, false)

        this.setHasOptionsMenu(false)

        setupViews(view)
        return view
    }

    private fun setupViews(view: View) {
        view.submit.setOnClickListener {
            bugViewModel.addBug(view.title.text.toString(), view.body.text.toString())
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}