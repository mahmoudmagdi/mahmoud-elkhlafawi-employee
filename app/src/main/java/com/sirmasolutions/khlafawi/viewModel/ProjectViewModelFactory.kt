package com.sirmasolutions.khlafawi.viewModel

import androidx.collection.ArrayMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sirmasolutions.khlafawi.di.ViewModelSubComponent
import java.lang.Exception
import java.lang.RuntimeException
import java.util.concurrent.Callable
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ProjectViewModelFactory @Inject constructor(viewModelSubComponent: ViewModelSubComponent) :
    ViewModelProvider.Factory {

    private val creators: ArrayMap<Class<*>, Callable<out ViewModel>> = ArrayMap()

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        var creator = creators[modelClass]
        if (creator == null) {
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }
        requireNotNull(creator) { "Unknown model class $modelClass" }
        return try {
            creator.call() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    init {

        // View models cannot be injected directly because they won't be bound to the owner's view model scope.
        creators[ResultsViewModel::class.java] = Callable<ViewModel> { viewModelSubComponent.resultsViewModel() }
    }
}