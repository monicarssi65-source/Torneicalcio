package com.torneicalcio.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.torneicalcio.app.data.*
import kotlinx.coroutines.launch

class MainViewModel(app: Application) : AndroidViewModel(app) {
    private val db = AppDatabase.getDatabase(app)
    private val tDao = db.tournamentDao()
    private val teamDao = db.teamDao()
    private val matchDao = db.matchDao()

    val allTournaments: LiveData<List<Tournament>> = tDao.getAll()

    fun getTournament(id: Long) = tDao.getById(id)
    fun getTeams(tid: Long) = teamDao.getByTournament(tid)
    fun getMatches(tid: Long) = matchDao.getByTournament(tid)

    fun insertTournament(t: Tournament) = viewModelScope.launch { tDao.insert(t) }
    fun deleteTournament(t: Tournament) = viewModelScope.launch { tDao.delete(t) }

    fun insertTeam(t: Team) = viewModelScope.launch { teamDao.insert(t) }
    fun deleteTeam(t: Team) = viewModelScope.launch { teamDao.delete(t) }

    fun insertMatch(m: Match) = viewModelScope.launch { matchDao.insert(m) }
    fun updateMatch(m: Match) = viewModelScope.launch { matchDao.update(m) }
    fun deleteMatch(m: Match) = viewModelScope.launch { matchDao.delete(m) }

    fun generateCalendar(tournamentId: Long, teams: List<Team>) = viewModelScope.launch {
        matchDao.deleteGroupMatches(tournamentId)
        val groups = teams.groupBy { it.groupName }
        groups.forEach { (groupName, groupTeams) ->
            for (i in groupTeams.indices) {
                for (j in i + 1 until groupTeams.size) {
                    matchDao.insert(
                        Match(
                            tournamentId = tournamentId,
                            groupName = groupName,
                            homeName = groupTeams[i].name,
                            awayName = groupTeams[j].name
                        )
                    )
                }
            }
        }
    }

    fun deleteFinals(tournamentId: Long) = viewModelScope.launch {
        matchDao.deleteFinalMatches(tournamentId)
    }
}
