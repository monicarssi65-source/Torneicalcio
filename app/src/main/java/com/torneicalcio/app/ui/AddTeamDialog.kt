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
    private var _binding: DialogAddTeamBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DialogAddTeamBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSave.setOnClickListener { saveTeam() }
        binding.btnCancel.setOnClickListener { dismiss() }
    }

    private fun saveTeam() {
        val name = binding.editTextTeamName.text.toString().trim()
        val coach = binding.editTextCoach.text.toString().trim()
        val phone = binding.editTextPhone.text.toString().trim()
        val email = binding.editTextEmail.text.toString().trim()
        val playersCount = binding.editTextPlayersCount.text.toString().toIntOrNull() ?: 0

        if (name.isEmpty()) { binding.editTextTeamName.error = "Obbligatorio"; return }

        val team = Team(tournamentId = tournamentId, name = name, coach = coach, phone = phone, email = email, playersCount = playersCount)
        onSave(team)
        dismiss()
    }
    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}