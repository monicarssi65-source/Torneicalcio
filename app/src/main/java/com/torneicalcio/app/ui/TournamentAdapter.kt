package com.torneicalcio.app.ui
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.torneicalcio.app.data.Tournament
import com.torneicalcio.app.databinding.ItemTournamentBinding

class TournamentAdapter(private val click: (Tournament) -> Unit) : ListAdapter<Tournament, TournamentAdapter.VH>(Diff()) {
    override fun onCreateViewHolder(p: ViewGroup, v: Int) = VH(ItemTournamentBinding.inflate(LayoutInflater.from(p.context), p, false))
    override fun onBindViewHolder(h: VH, p: Int) { h.bind(getItem(p)) }
    inner class VH(private val b: ItemTournamentBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(t: Tournament) {
            b.name.text = t.name
            b.info.text = t.category + " • " + t.startDate
            b.root.setOnClickListener { click(t) }
        }
    }
    class Diff : DiffUtil.ItemCallback<Tournament>() {
        override fun areItemsTheSame(o: Tournament, n: Tournament) = o.id == n.id
        override fun areContentsTheSame(o: Tournament, n: Tournament) = o == n
    }
}