package com.sirmasolutions.khlafawi.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlin.collections.ArrayList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sirmasolutions.khlafawi.service.repository.ProjectRepo
import com.sirmasolutions.khlafawi.R
import com.sirmasolutions.khlafawi.service.model.Project
import com.sirmasolutions.khlafawi.view.adapter.ProjectsAdapter

class MainActivity : AppCompatActivity() {

    private var projectsRecyclerView: RecyclerView? = null
    private var projectsAdapter: ProjectsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. read date from file
        val dataArray = ProjectRepo.getDataFromTextFile(this, "dataset3.txt")

        // 2. calculate and sort results
        val projectsArrayList = ProjectRepo.filterResults(dataArray)

        // 3. set up view
        initView(projectsArrayList)
    }

    private fun initView(projectsArrayList: ArrayList<Project>) {
        projectsRecyclerView = findViewById(R.id.projects_list)
        projectsRecyclerView?.layoutManager = LinearLayoutManager(this)
        projectsAdapter = ProjectsAdapter(this, projectsArrayList)
        projectsRecyclerView?.adapter = projectsAdapter
    }
}
