package com.joinhonor.bugtracker.dagger

import android.app.Application
import com.joinhonor.bugtracker.BugTrackerApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        ActivityBindingModule::class,
        ViewModelModule::class,
        NetworkModule::class]
)
interface ApplicationComponent : AndroidInjector<BugTrackerApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(application: Application)
}