package com.sirmasolutions.khlafawi.services.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sirmasolutions.khlafawi.MyApplication
import com.sirmasolutions.khlafawi.getDateFromString
import com.sirmasolutions.khlafawi.services.model.Project
import com.sirmasolutions.khlafawi.services.model.Record
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.NullPointerException
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.ArrayList
import kotlin.math.abs
import kotlin.math.ceil

@Singleton
class ProjectRepository {

    @Inject
    fun getProjectList(myApplication: Application): LiveData<List<Project>> {

        val dataArray: ArrayList<Record> = getDataFromTextFile(myApplication, "dataset3.txt")
        val projectsArrayList: ArrayList<Project> = getProjectsListWithDaysOverlapping(dataArray)
        val data: MutableLiveData<List<Project>> = MutableLiveData<List<Project>>()
        data.value = projectsArrayList

        return data
    }

    @Inject
    fun getDataFromTextFile(
        myApplication: Application,
        fileName: String
    ): ArrayList<Record> {

        val dataArray = ArrayList<Record>()

        // first get the file from the assets
        val mInputStream: InputStream = myApplication.assets.open(fileName)

        mInputStream.use { inputStream ->

            // start reading (line by line)
            val r = BufferedReader(InputStreamReader(inputStream))

            // wait for the start delimiter
            var line: String

            // this is the pattern for the data "int, int, String, String":
            val p: Pattern = Pattern.compile("(\\d+),(\\d+),(.+),(.+)")

            try { // try & catch for the null-pointer exception when the reader reach the end of text file

                // read it line by line
                while (r.readLine().also { line = it } != null) {

                    // if the data matches the pattern
                    val m: Matcher = p.matcher(line.replace("\\s".toRegex(), ""))
                    if (m.matches()) {

                        // handle it!
                        val employeeId = m.group(1)?.toInt()
                        val projectId = m.group(2)?.toInt()
                        val startingDate =
                            getDateFromString(m.group(3) /* covert string into date*/)
                        val endingDate = getDateFromString(m.group(4) /* covert string into date*/)

                        if (employeeId != null
                            && projectId != null
                            && startingDate != null
                            && endingDate != null
                        ) {
                            dataArray.add(Record(employeeId, projectId, startingDate, endingDate))
                        }
                    }
                }
            } catch (nullPointerException: NullPointerException) {
                //Log.i(TAG, "text file is finished")
            }
        }

        return dataArray
    }

    @Inject
    fun getProjectsListWithDaysOverlapping(dataArray: ArrayList<Record>): ArrayList<Project> {
        val projectsArrayList = ArrayList<Project>()

        for (item1 in dataArray) {

            for (item2 in dataArray) {

                if (item1.projectId == item2.projectId /* the same project */
                    && item1.employeeId != item2.employeeId /* not the same employee */) {

                    val daysOverlap = getOverlapDays(
                        item1.startingDate,
                        item1.endingDate,
                        item2.startingDate,
                        item2.endingDate
                    )

                    val projectData = Project(
                        item1.projectId,
                        item1.employeeId,
                        item2.employeeId,
                        daysOverlap
                    )

                    /* Create the same object with inverting employee IDs (this is the same record)*/
                    val invertedProjectData = Project(
                        item1.projectId,
                        item2.employeeId,
                        item1.employeeId,
                        daysOverlap
                    )

                    if (!projectsArrayList.contains(projectData)
                        && !projectsArrayList.contains(invertedProjectData)
                    ) {
                        projectsArrayList.add(projectData)
                    }
                }
            }
        }

        return projectsArrayList
    }

    @Inject
    fun getOverlapDays(
        startDate_emp1: Date,
        endDate_emp1: Date,
        startDate_emp2: Date,
        endDate_emp2: Date
    ): Int {

        val start = if (startDate_emp1 < startDate_emp2) {
            startDate_emp2
        } else {
            startDate_emp1
        }

        val end = if (endDate_emp1 < endDate_emp2) {
            endDate_emp1
        } else {
            endDate_emp2
        }

        if (end >= start) {
            val diff = abs(end.time - start.time) //get the difference between two dates

            return ceil((diff / (1000 /* m-seconds */ * 60 /* minutes */ * 60 /* seconds */ * 24 /* hours */)).toDouble()).toInt()
        }

        return 0
    }
}