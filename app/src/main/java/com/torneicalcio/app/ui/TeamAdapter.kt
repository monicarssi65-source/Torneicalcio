package com.torneicalcio.app.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.torneicalcio.app.data.Team
import com.torneicalcio.app.databinding.ItemTeamBinding

class TeamAdapter(
    private val onDelete: (Team) -> Unit
) : ListAdapter<Team, TeamAdapter.VH>(Diff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemTeamBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))

    inner class VH(private val b: ItemTeamBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(t: Team) {
            b.tvName.text = t.name
            b.tvGroup.text = if (t.groupName.isNotEmpty()) "Girone: ${t.groupName}" else "Nessun girone"
            try { b.colorBar.setBackgroundColor(Color.parseColor(t.color)) }
            catch (e: Exception) { b.colorBar.setBackgroundColor(Color.parseColor("#C62828")) }
            b.btnDelete.setOnClickListener { onDelete(t) }
        }
    }

    class Diff : DiffUtil.ItemCallback<Team>() {
        override fun areItemsTheSame(o: Team, n: Team) = o.id == n.id
        override fun areContentsTheSame(o: Team, n: Team) = o == n
    }
}
