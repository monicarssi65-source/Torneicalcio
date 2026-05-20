package com.torneicalcio.app.utils
import com.torneicalcio.app.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object TournamentGenerator {
    suspend fun generate(tid: Long, teams: List<Team>, groups: List<Group>, type: String, dao: MatchDao) = withContext(Dispatchers.IO) {
        val matches = mutableListOf<Match>()
        if (type == "groups" || type == "mixed") {
            groups.forEach { g ->
                val gTeams = teams.filter { it.id % groups.size == groups.indexOf(g) % groups.size }
                matches.addAll(roundRobin(g.id, gTeams, tid))
            }
        }
        if (type == "knockout" || type == "mixed") {
            matches.add(Match(tournamentId = tid, stage = "quarter", homeName = "Q1", awayName = "Q2", round = 1))
            matches.add(Match(tournamentId = tid, stage = "semi", homeName = "SF1", awayName = "SF2", round = 1))
            matches.add(Match(tournamentId = tid, stage = "final", homeName = "F1", awayName = "F2", round = 1))
        }
        dao.insertAll(matches)
    }
    private fun roundRobin(gid: Long, teams: List<Team>, tid: Long): List<Match> {
        val m = mutableListOf<Match>()
        for (i in teams.indices) for (j in i+1 until teams.size) {
            m.add(Match(tournamentId = tid, groupId = gid, stage = "group", homeTeamId = teams[i].id, awayTeamId = teams[j].id, homeName = teams[i].name, awayName = teams[j].name, round = 1))
            m.add(Match(tournamentId = tid, groupId = gid, stage = "group", homeTeamId = teams[j].id, awayTeamId = teams[i].id, homeName = teams[j].name, awayName = teams[i].name, round = 2))
        }
        return m
    }
}