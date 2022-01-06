package com.sirmasolutions.khlafawi.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.widget.TextView
import com.sirmasolutions.khlafawi.R
import com.sirmasolutions.khlafawi.service.model.Project

class ProjectsAdapter constructor(private val context: Context?, projectsList: List<Project>) :
    RecyclerView.Adapter<ProjectsAdapter.ProjectsViewHolder>() {

    private var projectsList: List<Project>? = null
    private var mInflater: LayoutInflater? = null

    init {
        mInflater = LayoutInflater.from(context)
        this.projectsList = projectsList
    }

    class ProjectsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var projectId: TextView? = null
        var firstEmployee: TextView? = null
        var secondEmployee: TextView? = null
        var overlappedDays: TextView? = null

        init {
            projectId = itemView.findViewById(R.id.project_id)
            firstEmployee = itemView.findViewById(R.id.first_employee)
            secondEmployee = itemView.findViewById(R.id.second_employee)
            overlappedDays = itemView.findViewById(R.id.days_overlap)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectsViewHolder {
        val view: View = mInflater!!.inflate(R.layout.item_project, parent, false)
        return ProjectsViewHolder(view)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ProjectsViewHolder, position: Int) {
        val projectData = projectsList?.get(position)

        holder.projectId?.text = projectData?.projectId.toString()
        holder.firstEmployee?.text = projectData?.firstEmployeeId.toString()
        holder.secondEmployee?.text = projectData?.secondEmployeeId.toString()
        holder.overlappedDays?.text = projectData?.daysOverlap.toString()

        if (position == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.projectId?.setTextColor(context?.resources?.getColor(R.color.red, null)!!)
                holder.firstEmployee?.setTextColor(
                    context?.resources?.getColor(
                        R.color.red,
                        null
                    )!!
                )
                holder.secondEmployee?.setTextColor(
                    context?.resources?.getColor(
                        R.color.red,
                        null
                    )!!
                )
                holder.overlappedDays?.setTextColor(
                    context?.resources?.getColor(
                        R.color.red,
                        null
                    )!!
                )
            } else {
                holder.projectId?.setTextColor(context?.resources?.getColor(R.color.red)!!)
                holder.firstEmployee?.setTextColor(context?.resources?.getColor(R.color.red)!!)
                holder.secondEmployee?.setTextColor(context?.resources?.getColor(R.color.red)!!)
                holder.overlappedDays?.setTextColor(context?.resources?.getColor(R.color.red)!!)
            }
        }
    }

    override fun getItemCount(): Int {
        return projectsList?.size ?: 0
    }
}