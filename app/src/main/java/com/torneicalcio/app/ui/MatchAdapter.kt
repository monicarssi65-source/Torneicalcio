package com.torneicalcio.app.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.torneicalcio.app.data.Match
import com.torneicalcio.app.databinding.ItemMatchBinding

class MatchAdapter(
    private val onClick: (Match) -> Unit
) : ListAdapter<Match, MatchAdapter.VH>(Diff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemMatchBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))

    inner class VH(private val b: ItemMatchBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(m: Match) {
            b.tvHome.text = m.homeName
            b.tvAway.text = m.awayName
            b.tvScore.text = if (m.isPlayed) "${m.homeScore} - ${m.awayScore}" else "vs"
            b.tvGroup.text = if (m.isFinal) "🏆 ${m.finalStage}" else "⚽ ${m.groupName}"
            b.tvInfo.text = buildString {
                if (m.field.isNotEmpty()) append("🏟️ ${m.field}")
                if (m.matchTime.isNotEmpty()) append("  🕐 ${m.matchTime}")
            }
            b.root.setOnClickListener { onClick(m) }
        }
    }

    class Diff : DiffUtil.ItemCallback<Match>() {
        override fun areItemsTheSame(o: Match, n: Match) = o.id == n.id
        override fun areContentsTheSame(o: Match, n: Match) = o == n
    }
}
