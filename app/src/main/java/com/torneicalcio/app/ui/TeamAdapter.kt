package com.torneicalcio.app.ui
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.torneicalcio.app.data.Team
import com.torneicalcio.app.databinding.ItemTeamBinding

class TeamAdapter(private val click: (Team) -> Unit) : ListAdapter<Team, TeamAdapter.VH>(Diff()) {
    override fun onCreateViewHolder(p: ViewGroup, v: Int) = VH(ItemTeamBinding.inflate(LayoutInflater.from(p.context), p, false))
    override fun onBindViewHolder(h: VH, p: Int) { h.bind(getItem(p)) }
    inner class VH(private val b: ItemTeamBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(t: Team) {
            b.name.text = t.name
            b.info.text = "All: " + t.coach
            b.root.setOnClickListener { click(t) }
        }
    }
    class Diff : DiffUtil.ItemCallback<Team>() {
        override fun areItemsTheSame(o: Team, n: Team) = o.id == n.id
        override fun areContentsTheSame(o: Team, n: Team) = o == n
    }
}