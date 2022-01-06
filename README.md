# Employee Pair

Task: Pair of employees that have worked as a team for the longest time.

![alt text](https://github.com/mahmoudmagdi/mahmoud-elkhlafawi-employee/image.gif)

## Days overlapping

The question is how to get the shared timeframe between two different employees in the same project
```kotlin
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
```

## Filter sort results
The main step is to calculate, filter, and sort results and then arrange them into projects

```kotlin
fun filterResults(dataArray: ArrayList<Record>): ArrayList<Project> {
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

        Collections.sort(projectsArrayList, CustomComparator())

        return projectsArrayList
    }
```