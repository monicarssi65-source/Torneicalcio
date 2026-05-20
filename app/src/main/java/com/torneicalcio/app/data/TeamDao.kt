package com.torneicalcio.app.data
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDao {
    @Insert suspend fun insert(t: Team): Long
    @Query("SELECT * FROM teams WHERE tournamentId = :tid ORDER BY name") fun getByTournament(tid: Long): Flow<List<Team>>
    @Query("SELECT COUNT(*) FROM teams WHERE tournamentId = :tid") fun count(tid: Long): Flow<Int>
}