package com.sirmasolutions.khlafawi.service.model

data class Project(
    val projectId: Int,
    val firstEmployeeId: Int,
    val secondEmployeeId: Int,
    val daysOverlap: Int
)