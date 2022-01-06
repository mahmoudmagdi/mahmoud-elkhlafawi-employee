package com.sirmasolutions.khlafawi.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.sirmasolutions.khlafawi.R
import com.sirmasolutions.khlafawi.databinding.FragmentResultsBinding
import com.sirmasolutions.khlafawi.di.Injectable
import com.sirmasolutions.khlafawi.view.adapter.ProjectsAdapter
import com.sirmasolutions.khlafawi.viewModel.ResultsViewModel
import javax.inject.Inject

class ResultsFragment : Fragment(), Injectable {

    companion object {
        val TAG = ResultsFragment::class.java.simpleName
    }

    private var projectsAdapter: ProjectsAdapter? = null
    private var binding: FragmentResultsBinding? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_results, container, false)
        projectsAdapter = ProjectsAdapter()
        binding?.projectList?.adapter = projectsAdapter
        binding?.isLoading = true

        return binding?.root as View
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel = ViewModelProviders.of(
            this,
            viewModelFactory
        ).get(ResultsViewModel::class.java)
        observeViewModel(viewModel)
    }

    private fun observeViewModel(viewModel: ResultsViewModel) {

        // Update the list when the data is ready
        viewModel.resultsObservable.observe(viewLifecycleOwner,
            { projects ->
                if (projects != null) {
                    binding?.isLoading = false
                    projectsAdapter?.setProjectList(projects)
                }
            })
    }
}