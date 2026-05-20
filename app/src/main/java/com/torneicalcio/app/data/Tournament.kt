package com.torneicalcio.app.data
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tournaments")
data class Tournament(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String, val category: String, val type: String,
    val startDate: String, val endDate: String, val location: String,
    val maxTeams: Int, val fee: Double, val description: String = "",
    val createdAt: Long = System.currentTimeMillis()
)