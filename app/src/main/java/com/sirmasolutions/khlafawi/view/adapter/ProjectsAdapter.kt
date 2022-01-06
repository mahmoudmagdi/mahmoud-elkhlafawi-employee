package com.sirmasolutions.khlafawi.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sirmasolutions.khlafawi.R
import com.sirmasolutions.khlafawi.databinding.ProjectItemBinding
import com.sirmasolutions.khlafawi.services.model.Project

class ProjectsAdapter : RecyclerView.Adapter<ProjectsAdapter.ProjectViewHolder>() {

    private lateinit var projectList: List<Project>

    fun setProjectList(projectList: List<Project>) {
        val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return this@ProjectsAdapter.projectList.size
            }

            override fun getNewListSize(): Int {
                return projectList.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return this@ProjectsAdapter.projectList[oldItemPosition] === projectList[newItemPosition]
            }

            override fun areContentsTheSame(
                oldItemPosition: Int,
                newItemPosition: Int
            ): Boolean {
                val project = projectList[newItemPosition]
                val old = projectList[oldItemPosition]
                return (project === old)
            }
        })
        this.projectList = projectList
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val binding: ProjectItemBinding = DataBindingUtil
            .inflate(
                LayoutInflater.from(parent.context), R.layout.project_item,
                parent, false
            )

        return ProjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder.binding.project = projectList[position]
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return projectList.size
    }

    class ProjectViewHolder(val binding: ProjectItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}