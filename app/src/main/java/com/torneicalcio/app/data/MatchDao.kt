package com.torneicalcio.app.data
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(m: Match): Long
    @Insert suspend fun insertAll(matches: List<Match>)
    @Update suspend fun update(m: Match)
    @Query("SELECT * FROM matches WHERE tournamentId = :tid ORDER BY date, time") fun getByTournament(tid: Long): Flow<List<Match>>
    @Query("UPDATE matches SET homeScore = :hs, awayScore = :as, isPlayed = 1 WHERE id = :id")
    suspend fun updateScore(id: Long, hs: Int, as: Int)
}