package com.sirmasolutions.khlafawi.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.sirmasolutions.khlafawi.services.model.Project
import com.sirmasolutions.khlafawi.services.repository.ProjectRepository
import javax.inject.Inject
import javax.inject.Singleton

class ResultsViewModel @Inject constructor(
    projectRepository: ProjectRepository,
    application: Application
) : AndroidViewModel(application) {

    /**
     * Expose the LiveData Projects query so the UI can observe it.
     */
    val resultsObservable: LiveData<List<Project>> = projectRepository.getProjectList(application)
}