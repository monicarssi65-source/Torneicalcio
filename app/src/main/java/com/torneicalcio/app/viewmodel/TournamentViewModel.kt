package com.torneicalcio.app.viewmodel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.torneicalcio.app.data.AppDatabase
import com.torneicalcio.app.data.Tournament
import kotlinx.coroutines.launch

class TournamentViewModel(app: Application) : AndroidViewModel(app) {
    private val dao = AppDatabase.getDatabase(app).tournamentDao()
    val allTournaments: LiveData<List<Tournament>> = dao.getAll().asLiveData()
    fun insert(t: Tournament) = viewModelScope.launch { dao.insert(t) }
    fun delete(t: Tournament) = viewModelScope.launch { dao.delete(t) }
}