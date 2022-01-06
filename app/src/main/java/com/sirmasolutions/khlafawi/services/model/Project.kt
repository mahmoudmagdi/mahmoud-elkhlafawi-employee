package com.sirmasolutions.khlafawi.services.model

data class Project(
    val projectId: Int,
    val firstEmployeeId: Int,
    val secondEmployeeId: Int,
    val daysOverlap: Int
)