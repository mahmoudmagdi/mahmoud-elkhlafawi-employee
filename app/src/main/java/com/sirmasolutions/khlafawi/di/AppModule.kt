package com.sirmasolutions.khlafawi.di

import androidx.lifecycle.ViewModelProvider
import com.sirmasolutions.khlafawi.viewModel.ProjectViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(subcomponents = [ViewModelSubComponent::class])
internal class AppModule {

    @Singleton
    @Provides
    fun provideViewModelFactory(
        viewModelSubComponent: ViewModelSubComponent.Builder
    ): ViewModelProvider.Factory {
        return ProjectViewModelFactory(viewModelSubComponent.build())
    }
}