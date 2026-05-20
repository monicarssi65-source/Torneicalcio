package com.torneicalcio.app.data
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "matches", foreignKeys = [
    ForeignKey(entity = Tournament::class, parentColumns = ["id"], childColumns = ["tournamentId"], onDelete = ForeignKey.CASCADE),
    ForeignKey(entity = Group::class, parentColumns = ["id"], childColumns = ["groupId"], onDelete = ForeignKey.SET_NULL),
    ForeignKey(entity = Team::class, parentColumns = ["id"], childColumns = ["homeTeamId"], onDelete = ForeignKey.SET_NULL),
    ForeignKey(entity = Team::class, parentColumns = ["id"], childColumns = ["awayTeamId"], onDelete = ForeignKey.SET_NULL)
], indices = [Index("tournamentId"), Index("groupId")])
data class Match(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val tournamentId: Long, val groupId: Long? = null,
    val stage: String,
    val homeTeamId: Long? = null, val awayTeamId: Long? = null,
    val homeName: String = "", val awayName: String = "",
    val field: String = "", val date: String = "", val time: String = "",
    val homeScore: Int? = null, val awayScore: Int? = null,
    val isPlayed: Boolean = false, val round: Int = 1,
    val createdAt: Long = System.currentTimeMillis()
)