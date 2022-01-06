package com.sirmasolutions.khlafawi.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlin.collections.ArrayList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sirmasolutions.khlafawi.service.repository.ProjectRepo
import com.sirmasolutions.khlafawi.R
import com.sirmasolutions.khlafawi.service.model.Project
import com.sirmasolutions.khlafawi.view.adapter.ProjectsAdapter
import android.content.Intent
import android.net.Uri

class MainActivity : AppCompatActivity() {

    private val PICK_FILE_RESULT_CODE = 1010

    private var projectsRecyclerView: RecyclerView? = null
    private var selectFileBtn: Button? = null
    private var projectsAdapter: ProjectsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        selectFileBtn = findViewById(R.id.select_file)
        selectFileBtn?.setOnClickListener {
            openSelectFileIntent()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_FILE_RESULT_CODE && resultCode == RESULT_OK) {
            val contentDescriber: Uri? = data?.data

            if (contentDescriber != null) {
                // 1. read date from file
                val dataArray = ProjectRepo.getDataFromTextFile(this, contentDescriber)

                // 2. calculate and sort results
                val projectsArrayList = ProjectRepo.filterResults(dataArray)

                // 3. set up view
                initView(projectsArrayList)
            }
        }
    }

    private fun initView(projectsArrayList: ArrayList<Project>) {
        projectsRecyclerView = findViewById(R.id.projects_list)
        projectsRecyclerView?.layoutManager = LinearLayoutManager(this)
        projectsAdapter = ProjectsAdapter(this, projectsArrayList)
        projectsRecyclerView?.adapter = projectsAdapter
    }

    private fun openSelectFileIntent() {
        val chooseFile = Intent(Intent.ACTION_GET_CONTENT)
        chooseFile.addCategory(Intent.CATEGORY_OPENABLE)
        chooseFile.type = "*/*"
        startActivityForResult(
            Intent.createChooser(chooseFile, "Choose a text file"),
            PICK_FILE_RESULT_CODE
        )
    }
}
