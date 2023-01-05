package com.maku.myfitness.ui.screens.details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maku.myfitness.core.data.offline.model.WorkOut
import com.maku.myfitness.ui.domain.usecases.GetAllWorkOut
import com.maku.myfitness.ui.domain.usecases.GetAllWorkOutByID
import com.maku.myfitness.ui.domain.usecases.GetAllWorkOutByName
import com.maku.myfitness.ui.screens.details.WorkOutDetailsDestination
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
class HomeDetailsViewModel @Inject constructor(
    private val getAllWorkOutByID: GetAllWorkOutByID,
    private val getAllWorkOutByName: GetAllWorkOutByName,
    savedStateHandle: SavedStateHandle,
    ): ViewModel() {

    private val workOutId: Int = checkNotNull(savedStateHandle[WorkOutDetailsDestination.workoutItemIdArg])
    private val workOutImageId: Int = checkNotNull(savedStateHandle[WorkOutDetailsDestination.workoutImageIdArg])
    private val workOutNameId: String = checkNotNull(savedStateHandle[WorkOutDetailsDestination.workoutImageNameArg])
    val workOutInfo: Flow<WorkOut> = getAllWorkOutByID(workOutId)

    // TODO: you should probably use a different state here ...
    private val _state = MutableStateFlow(WorkOutDetailsViewState())
    val state: StateFlow<WorkOutDetailsViewState> get() = _state

    init {
        _state.value = WorkOutDetailsViewState()
        subscribeToWorkOutDetails()
    }

    private fun subscribeToWorkOutDetails() {
        _state.value = state.value.copy( loading = true)
        viewModelScope.launch {
            getAllWorkOutByName(workOutNameId).collect { it ->
                _state.value = state.value.copy(loading = false, workOuts = it)
            }
        }
    }

}