package com.torneicalcio.app.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.torneicalcio.app.data.Match
import com.torneicalcio.app.data.Team
import com.torneicalcio.app.databinding.ActivityTournamentDetailBinding
import com.torneicalcio.app.viewmodel.MainViewModel

class TournamentDetailActivity : AppCompatActivity() {
    private lateinit var b: ActivityTournamentDetailBinding
    private val vm: MainViewModel by viewModels()
    private var tid: Long = 0
    private var currentTeams: List<Team> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityTournamentDetailBinding.inflate(layoutInflater)
        setContentView(b.root)
        tid = intent.getLongExtra("TID", 0)
        setSupportActionBar(b.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        vm.getTournament(tid).observe(this) { t ->
            t?.let { supportActionBar?.title = it.name }
        }

        setupTeamsTab()
        setupMatchesTab()

        b.btnTabTeams.setOnClickListener { showTab("teams") }
        b.btnTabMatches.setOnClickListener { showTab("matches") }
        b.btnTabStandings.setOnClickListener { showTab("standings") }

        b.fabTeam.setOnClickListener {
            AddTeamDialog(tid) { team -> vm.insertTeam(team) }
                .show(supportFragmentManager, "addTeam")
        }

        b.btnGenerateCalendar.setOnClickListener {
            vm.generateCalendar(tid, currentTeams)
            showTab("matches")
        }

        b.btnGoFinals.setOnClickListener {
            startActivity(Intent(this, MatchesActivity::class.java)
                .putExtra("TID", tid).putExtra("FINALS", true))
        }

        showTab("teams")
    }

    private fun setupTeamsTab() {
        val adapter = TeamAdapter { team ->
            androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Eliminare ${team.name}?")
                .setPositiveButton("Elimina") { _, _ -> vm.deleteTeam(team) }
                .setNegativeButton("Annulla", null).show()
        }
        b.rvTeams.layoutManager = LinearLayoutManager(this)
        b.rvTeams.adapter = adapter

        vm.getTeams(tid).observe(this) { list ->
            currentTeams = list
            adapter.submitList(list)
            b.tvTeamsEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun setupMatchesTab() {
        val adapter = MatchAdapter { match ->
            EditMatchDialog(match) { updated -> vm.updateMatch(updated) }
                .show(supportFragmentManager, "editMatch")
        }
        b.rvMatches.layoutManager = LinearLayoutManager(this)
        b.rvMatches.adapter = adapter

        vm.getMatches(tid).observe(this) { list ->
            val groupMatches = list.filter { !it.isFinal }
            adapter.submitList(groupMatches)
            b.tvMatchesEmpty.visibility = if (groupMatches.isEmpty()) View.VISIBLE else View.GONE
            updateStandings(groupMatches)
        }
    }

    private fun updateStandings(matches: List<Match>) {
        val played = matches.filter { it.isPlayed }
        if (played.isEmpty()) {
            b.tvStandings.text = "Nessuna partita giocata ancora."
            return
        }

        val stats = mutableMapOf<String, IntArray>() // pts, g, v, n, p, gf, gs
        played.forEach { m ->
            val h = stats.getOrPut(m.homeName) { IntArray(7) }
            val a = stats.getOrPut(m.awayName) { IntArray(7) }
            val hs = m.homeScore ?: 0
            val as_ = m.awayScore ?: 0
            h[1]++; a[1]++
            h[5] += hs; h[6] += as_
            a[5] += as_; a[6] += hs
            when {
                hs > as_ -> { h[0] += 3; h[2]++ ; a[4]++ }
                hs < as_ -> { a[0] += 3; a[2]++ ; h[4]++ }
                else -> { h[0]++; a[0]++; h[3]++; a[3]++ }
            }
        }

        val sorted = stats.entries.sortedWith(compareByDescending<Map.Entry<String, IntArray>> { it.value[0] }
            .thenByDescending { it.value[5] - it.value[6] }
            .thenByDescending { it.value[5] })

        val sb = StringBuilder()
        sb.appendLine("Pos  Squadra              Pt  G  V  N  P  GF GS")
        sb.appendLine("─".repeat(50))
        sorted.forEachIndexed { i, (name, s) ->
            sb.appendLine(String.format("%-4d %-20s %-3d %-3d %-3d %-3d %-3d %-3d %-3d",
                i + 1, name.take(20), s[0], s[1], s[2], s[3], s[4], s[5], s[6]))
        }
        b.tvStandings.text = sb.toString()
    }

    private fun showTab(tab: String) {
        b.panelTeams.visibility = if (tab == "teams") View.VISIBLE else View.GONE
        b.panelMatches.visibility = if (tab == "matches") View.VISIBLE else View.GONE
        b.panelStandings.visibility = if (tab == "standings") View.VISIBLE else View.GONE
        b.fabTeam.visibility = if (tab == "teams") View.VISIBLE else View.GONE
        b.btnGenerateCalendar.visibility = if (tab == "matches") View.VISIBLE else View.GONE

        val active = getColor(com.torneicalcio.app.R.color.red)
        val inactive = getColor(com.torneicalcio.app.R.color.gray)
        b.btnTabTeams.setTextColor(if (tab == "teams") active else inactive)
        b.btnTabMatches.setTextColor(if (tab == "matches") active else inactive)
        b.btnTabStandings.setTextColor(if (tab == "standings") active else inactive)
    }

    override fun onSupportNavigateUp(): Boolean { finish(); return true }
}
