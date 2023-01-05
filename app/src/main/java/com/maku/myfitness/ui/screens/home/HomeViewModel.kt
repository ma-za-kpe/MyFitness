package com.maku.myfitness.ui.screens.home

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maku.myfitness.core.data.offline.model.WorkOut
import com.maku.myfitness.ui.domain.usecases.GetAllWorkOut
import com.maku.myfitness.ui.domain.usecases.GetAllWorkOutByID
import com.maku.myfitness.ui.screens.details.WorkOutDetailsDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllWorkOut: GetAllWorkOut
    ): ViewModel() {
    private val _state = MutableStateFlow(HomeViewState())     // TODO: you should probably use a different state here ... or just consolidate all states like its done in the now in android app. (highly suggest)
    val state: StateFlow<HomeViewState> get() = _state


    init {
        _state.value = HomeViewState()
         subscribeToWorkOutDb()
    }

    private fun subscribeToWorkOutDb() {
        _state.value = state.value.copy( loading = true)
        viewModelScope.launch {
            getAllWorkOut().collect { it ->
                Log.d("TAG", "subscribeToWorkOutDb: size${it.distinctBy { it.Name }}")
                _state.value = state.value.copy(loading = false, workOuts = it.distinctBy { it.Name })
            }
        }
    }
}