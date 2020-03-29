package com.joinhonor.bugtracker.ui

import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.joinhonor.bugtracker.Bug
import com.joinhonor.bugtracker.R
import com.joinhonor.bugtracker.viewmodel.BugsViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.bug_item.*
import kotlinx.android.synthetic.main.bug_list.view.*
import javax.inject.Inject

class BugListFragment : DaggerFragment() {

    companion object {
        const val BUTTON_DELETE = "delete"
        const val BUTTON_ASSIGN = "assign"
        const val BUTTON_STATUS = "status"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var bugViewModel: BugsViewModel
    lateinit var bugAdapter: BugAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        bugViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory)
            .get(BugsViewModel::class.java)
        val view = inflater.inflate(R.layout.bug_list, container, false)
        this.setHasOptionsMenu(true)

        bugAdapter = BugAdapter{ bug: Bug, tag: String -> onBugDeleteClicked(bug, tag) }
        view.bug_list.adapter = bugAdapter
        bugAdapter.setBugList(bugViewModel.getBugList())

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.bugs_menu, menu)
        super.onCreateOptionsMenu(menu, menuInflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add_bug -> {
            bugViewModel.onAddButtonPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onBugDeleteClicked(bug : Bug, tag: String) {
        when(tag) {
            BUTTON_DELETE -> {
                bugViewModel.deleteBug(bug.id)
            }
            BUTTON_ASSIGN -> {
            }
            BUTTON_STATUS -> {

            }
        }
    }
}