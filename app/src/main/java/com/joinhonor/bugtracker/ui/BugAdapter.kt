package com.joinhonor.bugtracker.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joinhonor.bugtracker.Bug
import com.joinhonor.bugtracker.R
import kotlinx.android.synthetic.main.bug_item.view.*

class BugAdapter(val clickListener : (Bug, String) -> Unit) : RecyclerView.Adapter<BugViewHolder>() {


    var bugs : List<Bug> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BugViewHolder {
        return BugViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.bug_item, parent, false))
    }

    override fun getItemCount(): Int {
        return bugs.size
    }

    override fun onBindViewHolder(holder: BugViewHolder, position: Int) {
        holder.setBugView(bugs[position], clickListener)
    }

    fun setBugList(bugList : List<Bug>) {
        bugs = bugList
        notifyDataSetChanged()
    }
}

class BugViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    fun setBugView(bug :Bug, clickListener : (Bug, String) -> Unit) {
        val resorce = view.context.resources
        view.bug_title.text = resorce.getString(R.string.title).plus(bug.title)
        view.bug_assignedTo.text = resorce.getString(R.string.assignedTo).plus(bug.assignee)
        view.bug_status.text = resorce.getString(R.string.status).plus(bug.status)
        view.bug_delete.setOnClickListener { it -> clickListener(bug, it.tag as String) }
        view.bug_assign.setOnClickListener { it -> clickListener(bug, it.tag as String) }
    }
}