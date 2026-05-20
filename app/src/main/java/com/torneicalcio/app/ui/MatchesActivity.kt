package com.torneicalcio.app.ui
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.torneicalcio.app.databinding.ActivityMatchesBinding
import com.torneicalcio.app.viewmodel.MatchViewModel
import com.torneicalcio.app.viewmodel.ViewModelFactory
import com.torneicalcio.app.utils.ShareHelper

class MatchesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMatchesBinding
    private val vm: MatchViewModel by viewModels { ViewModelFactory(application) }
    private var tid: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tid = intent.getLongExtra("TID", 0)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val adapter = MatchAdapter { m -> /* Edit dialog */ }
        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.adapter = adapter

        vm.getByTournament(tid).observe(this) { list -> adapter.submitList(list) }

        binding.fab.setOnClickListener {
            AddMatchDialog(tid) { vm.insert(it) }.show(supportFragmentManager, "addMatch")
        }
        binding.btnShare.setOnClickListener {
            ShareHelper.shareWhatsApp(this, "Condivisione torneo $tid")
        }
    }
    override fun onSupportNavigateUp(): Boolean { finish(); return true }
}