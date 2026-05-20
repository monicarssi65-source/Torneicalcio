package com.torneicalcio.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.torneicalcio.app.data.Team
import com.torneicalcio.app.databinding.DialogAddTeamBinding

class AddTeamDialog(
    private val tournamentId: Long,
    private val onSave: (Team) -> Unit
) : DialogFragment() {
    private var _b: DialogAddTeamBinding? = null
    private val b get() = _b!!

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        _b = DialogAddTeamBinding.inflate(i, c, false)
        return b.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(v: View, s: Bundle?) {
        b.btnSave.setOnClickListener { save() }
        b.btnCancel.setOnClickListener { dismiss() }
    }

    private fun save() {
        val name = b.etName.text.toString().trim()
        if (name.isEmpty()) { b.etName.error = "Obbligatorio"; return }
        onSave(Team(
            tournamentId = tournamentId,
            name = name,
            groupName = b.etGroup.text.toString().trim(),
            color = b.etColor.text.toString().trim().ifEmpty { "#C62828" }
        ))
        dismiss()
    }

    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
