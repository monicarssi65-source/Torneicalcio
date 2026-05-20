package com.torneicalcio.app.ui
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.torneicalcio.app.databinding.ActivityTournamentDetailBinding
import com.torneicalcio.app.viewmodel.TeamViewModel
import com.torneicalcio.app.viewmodel.ViewModelFactory

class TournamentDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTournamentDetailBinding
    private val teamVm: TeamViewModel by viewModels { ViewModelFactory(application) }
    private var tid: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTournamentDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tid = intent.getLongExtra("TID", 0)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val adapter = TeamAdapter { t ->
            startActivity(Intent(this, MatchesActivity::class.java).putExtra("TID", tid).putExtra("TEAM_ID", t.id))
        }
        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.adapter = adapter

        teamVm.getByTournament(tid).observe(this) { list ->
            adapter.submitList(list)
            binding.empty.visibility = if (list.isEmpty()) android.view.View.VISIBLE else android.view.View.GONE
        }

        binding.btnMatches.setOnClickListener {
            startActivity(Intent(this, MatchesActivity::class.java).putExtra("TID", tid))
        }
    }
    override fun onSupportNavigateUp(): Boolean { finish(); return true }
}