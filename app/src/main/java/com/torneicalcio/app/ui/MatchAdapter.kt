package com.torneicalcio.app.ui
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.torneicalcio.app.data.Match
import com.torneicalcio.app.databinding.ItemMatchBinding

class MatchAdapter(private val click: (Match) -> Unit) : ListAdapter<Match, MatchAdapter.VH>(Diff()) {
    override fun onCreateViewHolder(p: ViewGroup, v: Int) = VH(ItemMatchBinding.inflate(LayoutInflater.from(p.context), p, false))
    override fun onBindViewHolder(h: VH, p: Int) { h.bind(getItem(p)) }
    inner class VH(private val b: ItemMatchBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(m: Match) {
            b.home.text = m.homeName
            b.away.text = m.awayName
            b.score.text = if(m.isPlayed) (m.homeScore.toString() + " - " + m.awayScore.toString()) else "vs"
            b.field.text = "️ " + m.field
            b.root.setOnClickListener { click(m) }
        }
    }
    class Diff : DiffUtil.ItemCallback<Match>() {
        override fun areItemsTheSame(o: Match, n: Match) = o.id == n.id
        override fun areContentsTheSame(o: Match, n: Match) = o == n
    }
}