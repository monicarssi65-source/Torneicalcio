package com.torneicalcio.app.ui
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.torneicalcio.app.databinding.ActivityMainBinding
import com.torneicalcio.app.viewmodel.TournamentViewModel
import com.torneicalcio.app.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val vm: TournamentViewModel by viewModels { ViewModelFactory(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "⚽ Tornei Calcio Giovanili"

        val adapter = TournamentAdapter { t ->
            startActivity(Intent(this, TournamentDetailActivity::class.java).putExtra("TID", t.id))
        }
        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.adapter = adapter

        vm.allTournaments.observe(this) { list ->
            adapter.submitList(list)
            binding.empty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        }

        binding.fab.setOnClickListener {
            AddTournamentDialog { vm.insert(it) }.show(supportFragmentManager, "addTournament")
        }
    }
}