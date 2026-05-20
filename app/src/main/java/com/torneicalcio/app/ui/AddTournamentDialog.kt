package com.torneicalcio.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.torneicalcio.app.data.Tournament
import com.torneicalcio.app.databinding.DialogAddTournamentBinding

class AddTournamentDialog(private val onSave: (Tournament) -> Unit) : DialogFragment() {
    private var _b: DialogAddTournamentBinding? = null
    private val b get() = _b!!

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        _b = DialogAddTournamentBinding.inflate(i, c, false)
        return b.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onViewCreated(v: View, s: Bundle?) {
        b.btnSave.setOnClickListener { save() }
        b.btnCancel.setOnClickListener { dismiss() }
    }

    private fun save() {
        val name = b.etName.text.toString().trim()
        val society = b.etSociety.text.toString().trim()
        if (name.isEmpty()) { b.etName.error = "Obbligatorio"; return }
        if (society.isEmpty()) { b.etSociety.error = "Obbligatorio"; return }

        onSave(Tournament(
            name = name,
            society = society,
            category = b.etCategory.text.toString().trim(),
            location = b.etLocation.text.toString().trim(),
            startDate = b.etDate.text.toString().trim(),
            numGroups = b.etGroups.text.toString().toIntOrNull() ?: 3,
            teamsPerGroup = b.etTeamsPerGroup.text.toString().toIntOrNull() ?: 3,
            fields = b.etFields.text.toString().trim()
        ))
        dismiss()
    }

    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
