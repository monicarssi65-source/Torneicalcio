package com.torneicalcio.app.data
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupDao {
    @Insert suspend fun insert(g: Group): Long
    @Query("SELECT * FROM groups WHERE tournamentId = :tid ORDER BY name") fun getByTournament(tid: Long): Flow<List<Group>>
}