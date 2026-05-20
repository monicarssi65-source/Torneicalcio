package com.torneicalcio.app.viewmodel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.torneicalcio.app.data.AppDatabase
import com.torneicalcio.app.data.Match
import kotlinx.coroutines.launch

class MatchViewModel(app: Application) : AndroidViewModel(app) {
    private val dao = AppDatabase.getDatabase(app).matchDao()
    fun getByTournament(tid: Long): LiveData<List<Match>> = dao.getByTournament(tid).asLiveData()
    fun insert(m: Match) = viewModelScope.launch { dao.insert(m) }
    fun updateScore(id: Long, hs: Int, as: Int) = viewModelScope.launch { dao.updateScore(id, hs, as) }
}