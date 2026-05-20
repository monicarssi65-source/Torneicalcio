package com.torneicalcio.app.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "matches",
    foreignKeys = [ForeignKey(
        entity = Tournament::class,
        parentColumns = ["id"],
        childColumns = ["tournamentId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("tournamentId")]
)
data class Match(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val tournamentId: Long,
    val groupName: String = "",
    val homeName: String,
    val awayName: String,
    val field: String = "",
    val matchTime: String = "",
    val homeScore: Int? = null,
    val awayScore: Int? = null,
    val isPlayed: Boolean = false,
    val isFinal: Boolean = false,
    val finalStage: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
