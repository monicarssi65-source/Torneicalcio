package com.torneicalcio.app.data
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "groups", foreignKeys = [ForeignKey(entity = Tournament::class, parentColumns = ["id"], childColumns = ["tournamentId"], onDelete = ForeignKey.CASCADE)], indices = [Index("tournamentId")])
data class Group(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val tournamentId: Long, val name: String,
    val createdAt: Long = System.currentTimeMillis()
)