package com.joinhonor.bugtracker.dagger

import com.joinhonor.bugtracker.ui.AddBugFragment
import com.joinhonor.bugtracker.ui.BugListFragment
import com.joinhonor.bugtracker.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    abstract fun listFragment(): BugListFragment

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    abstract fun addBugFragment(): AddBugFragment
}