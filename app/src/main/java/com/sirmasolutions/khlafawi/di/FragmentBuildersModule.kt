package com.sirmasolutions.khlafawi.di

import com.sirmasolutions.khlafawi.view.ui.ResultsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeResultsFragment(): ResultsFragment
}