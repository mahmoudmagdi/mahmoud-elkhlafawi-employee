package com.sirmasolutions.khlafawi

import java.util.*

data class Record(
    val employeeId: Int? = 0,
    val projectId: Int? = 0,
    val startingDate: Date? = Date(),
    val endingDate: Date? = Date(),
)