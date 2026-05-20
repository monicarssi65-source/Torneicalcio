package com.torneicalcio.app.viewmodel
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when {
        modelClass.isAssignableFrom(TournamentViewModel::class.java) -> TournamentViewModel(app) as T
        modelClass.isAssignableFrom(TeamViewModel::class.java) -> TeamViewModel(app) as T
        modelClass.isAssignableFrom(MatchViewModel::class.java) -> MatchViewModel(app) as T
        else -> throw IllegalArgumentException("Unknown ViewModel")
    }
}