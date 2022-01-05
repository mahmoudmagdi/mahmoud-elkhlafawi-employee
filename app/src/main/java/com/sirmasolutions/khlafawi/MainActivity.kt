package com.sirmasolutions.khlafawi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.NullPointerException
import java.util.regex.Matcher
import java.util.regex.Pattern

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
        // 2. Calculate all timeframes of projects per employee
        // 3. Get the smallest difference between two employees in one project
        // 4. Get the employee pairs who worked together for the longest time in the same project
        // 5. Show results


        val dataArray: ArrayList<Record> = getDataFromTextFile("dataset.txt")

        for (item in dataArray) {
            Log.d(
                TAG, "employeeId: ${item.employeeId.toString()} - " +
                        "projectId: ${item.projectId} - " +
                        "startDate: ${item.startingDate} - " +
                        "endDate: ${item.endingDate}"
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
            //while (r.readLine().also { line = it } != null) if (line == "[START]") break

            // this is the pattern for the data "int, int, String, String":
            val p: Pattern = Pattern.compile("(\\d+),(\\d+),(.+),(.+)")

            try {
                // read it line by line...
                while (r.readLine().also { line = it } != null) {

                    // till the end comes (end delimiter)
                    //if (line == "[END]") break //done

                    // if the data matches the pattern
                    val m: Matcher = p.matcher(line.replace("\\s".toRegex(), ""))
                    if (m.matches()) {

                        // handle it!
                        val employeeId = m.group(1)?.toInt()
                        val projectId = m.group(2)?.toInt()
                        val startingDate =
                            getDateFromString(m.group(3) /* covert string into date*/)
                        val endingDate = getDateFromString(m.group(4) /* covert string into date*/)

                        dataArray.add(Record(employeeId, projectId, startingDate, endingDate))
                    }
                }
            } catch (nullPointerException: NullPointerException) {
                Log.i(TAG, "text file is finished")
            }
        }

        return dataArray
    }
}