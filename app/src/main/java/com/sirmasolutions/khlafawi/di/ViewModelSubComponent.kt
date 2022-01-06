package com.sirmasolutions.khlafawi.di

import com.sirmasolutions.khlafawi.viewModel.ResultsViewModel
import dagger.Subcomponent

@Subcomponent
interface ViewModelSubComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): ViewModelSubComponent
    }

    fun resultsViewModel(): ResultsViewModel
}