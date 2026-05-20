package com.torneicalcio.app.ui
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.torneicalcio.app.data.Match
import com.torneicalcio.app.databinding.DialogAddMatchBinding
import java.util.*

class AddMatchDialog(
    private val tournamentId: Long,
    private val onSave: (Match) -> Unit
) : DialogFragment() {
    private var _binding: DialogAddMatchBinding? = null
    private val binding get() = _binding!!
    private var selectedDate = ""; var selectedTime = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DialogAddMatchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSelectDate.setOnClickListener { showDatePicker() }
        binding.btnSelectTime.setOnClickListener { showTimePicker() }
        binding.btnSave.setOnClickListener { saveMatch() }
        binding.btnCancel.setOnClickListener { dismiss() }
    }

    private fun showDatePicker() {
        val cal = Calendar.getInstance()
        DatePickerDialog(requireContext(), { _, y, m, d ->
            selectedDate = String.format("%04d-%02d-%02d", y, m+1, d)
            binding.btnSelectDate.text = "📅 " + selectedDate
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun showTimePicker() {
        val cal = Calendar.getInstance()
        TimePickerDialog(requireContext(), { _, h, m ->
            selectedTime = String.format("%02d:%02d", h, m)
            binding.btnSelectTime.text = "🕒 " + selectedTime
        }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
    }

    private fun saveMatch() {
        val home = binding.editTextHomeTeam.text.toString().trim()
        val away = binding.editTextAwayTeam.text.toString().trim()
        val field = binding.editTextField.text.toString().trim()
        val hScore = binding.editTextHomeScore.text.toString().toIntOrNull()
        val aScore = binding.editTextAwayScore.text.toString().toIntOrNull()

        if (home.isEmpty() || away.isEmpty()) return

        val match = Match(
            tournamentId = tournamentId, homeName = home, awayName = away,
            field = field, date = selectedDate, time = selectedTime,
            homeScore = hScore, awayScore = aScore,
            isPlayed = (hScore != null && aScore != null), stage = "group"
        )
        onSave(match)
        dismiss()
    }
    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}