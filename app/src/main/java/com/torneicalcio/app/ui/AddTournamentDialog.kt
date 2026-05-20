package com.torneicalcio.app.ui
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.torneicalcio.app.data.Tournament
import com.torneicalcio.app.databinding.DialogAddTournamentBinding
import java.util.*

class AddTournamentDialog(private val onSave: (Tournament) -> Unit) : DialogFragment() {
    private var _b: DialogAddTournamentBinding? = null
    private val b get() = _b!!
    private var start = ""; var end = ""

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        _b = DialogAddTournamentBinding.inflate(i, c, false); return b.root
    }
    override fun onViewCreated(v: View, s: Bundle?) {
        b.spinnerType.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, arrayOf("Gironi", "Eliminazione", "Misto"))
        b.btnStart.setOnClickListener { pickDate(true) }
        b.btnEnd.setOnClickListener { pickDate(false) }
        b.btnSave.setOnClickListener { save() }
        b.btnCancel.setOnClickListener { dismiss() }
    }
    private fun pickDate(isStart: Boolean) {
        val cal = Calendar.getInstance()
        DatePickerDialog(requireContext(), { _, y, m, d ->
            val ds = String.format("%04d-%02d-%02d", y, m+1, d)
            if (isStart) { start = ds; b.btnStart.text = ds } else { end = ds; b.btnEnd.text = ds }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
    }
    private fun save() {
        val t = Tournament(
            name = b.name.text.toString(), category = b.category.text.toString(),
            type = b.spinnerType.selectedItem.toString().lowercase(),
            startDate = start, endDate = end, location = b.location.text.toString(),
            maxTeams = b.max.text.toString().toIntOrNull() ?: 0,
            fee = b.fee.text.toString().toDoubleOrNull() ?: 0.0
        )
        if (t.name.isEmpty()) return
        onSave(t); dismiss()
    }
    override fun onDestroyView() { super.onDestroyView(); _b = null }
}