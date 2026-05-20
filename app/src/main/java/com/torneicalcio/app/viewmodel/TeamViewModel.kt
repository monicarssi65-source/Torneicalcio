package com.torneicalcio.app.viewmodel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.torneicalcio.app.data.AppDatabase
import com.torneicalcio.app.data.Team
import kotlinx.coroutines.launch

class TeamViewModel(app: Application) : AndroidViewModel(app) {
    private val dao = AppDatabase.getDatabase(app).teamDao()
    fun getByTournament(tid: Long): LiveData<List<Team>> = dao.getByTournament(tid).asLiveData()
    fun insert(t: Team) = viewModelScope.launch { dao.insert(t) }
}