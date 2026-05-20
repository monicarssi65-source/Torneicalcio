package com.torneicalcio.app.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.torneicalcio.app.data.Tournament
import com.torneicalcio.app.databinding.ItemTournamentBinding

class TournamentAdapter(
    private val onClick: (Tournament) -> Unit,
    private val onDelete: (Tournament) -> Unit
) : ListAdapter<Tournament, TournamentAdapter.VH>(Diff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemTournamentBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))

    inner class VH(private val b: ItemTournamentBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(t: Tournament) {
            b.tvName.text = t.name
            b.tvInfo.text = "${t.society} • ${t.category} • ${t.startDate}"
            b.tvLocation.text = "📍 ${t.location}"
            b.root.setOnClickListener { onClick(t) }
            b.btnDelete.setOnClickListener { onDelete(t) }
        }
    }

    class Diff : DiffUtil.ItemCallback<Tournament>() {
        override fun areItemsTheSame(o: Tournament, n: Tournament) = o.id == n.id
        override fun areContentsTheSame(o: Tournament, n: Tournament) = o == n
    }
}
