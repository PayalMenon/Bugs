package com.joinhonor.bugtracker.dagger

import androidx.lifecycle.ViewModel
import com.joinhonor.bugtracker.viewmodel.BugsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(BugsViewModel::class)
    abstract fun bindBugsViewModel(viewModel: BugsViewModel): ViewModel
}