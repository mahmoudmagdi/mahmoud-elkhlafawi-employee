package com.sirmasolutions.khlafawi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.NullPointerException
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.collections.ArrayList
import kotlin.math.abs
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        * The text file is in format:
        *   EmpID, ProjectID, DateFrom, DateTo
        *
        * Example data:
        *   143, 12, 2013-11-01, 2014,01-05
        *   218, 10, 2012-05-16, NULL
        *   143, 10, 2009-01-01, 2011-04-27
        * */

        //TODO:
        // 1. Convert text file into array of objects [DONE]
        // 2. Calculate all timeframes of projects per employee [DONE]
        // 3. Get the days overlap between two employees in one project [DONE]
        // 4. Get the employee pairs who worked together for the longest time in the same project [DONE]
        // 5. Show results [DONE]

        // 6. Pick file from the memory (optional)
        // 7. Support more data format (optional)


        val dataArray: ArrayList<Record> = getDataFromTextFile("dataset3.txt")
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

                    /* Create the same object with inversing employee IDs (this is the same record)*/
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

        for (item in projectsArrayList) {
            Log.d(
                TAG, "projectId: ${item.projectId} - " +
                        "firstEmployeeId: ${item.firstEmployeeId} - " +
                        "secondEmployeeId: ${item.secondEmployeeId} - " +
                        "daysOverlap: ${item.daysOverlap}"
            )
        }
    }

    private fun getDataFromTextFile(fileName: String): ArrayList<Record> {

        val dataArray = ArrayList<Record>()

        // first get the file from the assets
        val mInputStream: InputStream = assets.open(fileName)

        mInputStream.use { inputStream ->

            // start reading (line by line)
            val r = BufferedReader(InputStreamReader(inputStream))

            // wait for the start delimiter
            var line: String

            // this is the pattern for the data "int, int, String, String":
            val p: Pattern = Pattern.compile("(\\d+),(\\d+),(.+),(.+)")

            try { //try & catch for the null-pointer exception when the reader reach the end of text file

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
                Log.i(TAG, "text file is finished")
            }
        }

        return dataArray
    }

    private fun getOverlapDays(
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