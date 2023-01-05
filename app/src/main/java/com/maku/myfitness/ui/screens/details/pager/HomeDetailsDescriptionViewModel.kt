package com.maku.myfitness.ui.screens.details.pager

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maku.myfitness.core.data.offline.model.WorkOut
import com.maku.myfitness.ui.domain.usecases.GetAllWorkOut
import com.maku.myfitness.ui.domain.usecases.GetAllWorkOutByID
import com.maku.myfitness.ui.domain.usecases.GetAllWorkOutByName
import com.maku.myfitness.ui.screens.details.WorkOutDetailsDestination
import com.maku.myfitness.ui.screens.details.pager.WorkOutDetailsDescriptionDestination
import com.maku.myfitness.ui.screens.home.HomeViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class HomeDetailsDescriptionViewModel @Inject constructor(
    private val getAllWorkOutByID: GetAllWorkOutByID,
    private val getAllWorkOutByName: GetAllWorkOutByName,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val workOutName: String =
        checkNotNull(savedStateHandle[WorkOutDetailsDescriptionDestination.workoutItemNameIdArg])
    val workOutDescriptionId: Int =
        checkNotNull(savedStateHandle[WorkOutDetailsDescriptionDestination.workoutItemIdArg])

    // TODO: you should probably use a different state here ... or just consolidate all states like its done in the now in android app. ()
    private val _state = MutableStateFlow(WorkOutDetailsDescriptionViewState())
    val state: StateFlow<WorkOutDetailsDescriptionViewState> get() = _state

    init {
        _state.value = WorkOutDetailsDescriptionViewState()
        subscribeToWorkOutDetails()
    }

    private fun subscribeToWorkOutDetails() {
        _state.value = state.value.copy(loading = true)
        viewModelScope.launch {
            getAllWorkOutByName(workOutName).collect { it ->
                _state.value = state.value.copy(loading = false, workOuts = it)
            }
        }
    }

}