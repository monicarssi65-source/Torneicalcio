package com.torneicalcio.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tournaments")
data class Tournament(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val society: String,
    val category: String,
    val location: String,
    val startDate: String,
    val numGroups: Int = 3,
    val teamsPerGroup: Int = 3,
    val fields: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
