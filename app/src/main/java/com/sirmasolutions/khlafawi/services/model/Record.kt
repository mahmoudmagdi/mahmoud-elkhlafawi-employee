package com.sirmasolutions.khlafawi.services.model

import java.util.*

data class Record(
    val employeeId: Int,
    val projectId: Int,
    val startingDate: Date,
    val endingDate: Date,
)