package com.maku.myfitness.ui.screens.details

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
class HomeDetailsViewModel @Inject constructor(
    private val getAllWorkOutByID: GetAllWorkOutByID,
    savedStateHandle: SavedStateHandle,
    ): ViewModel() {

    private val workOutId: Int = checkNotNull(savedStateHandle[WorkOutDetailsDestination.workoutItemIdArg])
    private val workOutImageId: Int = checkNotNull(savedStateHandle[WorkOutDetailsDestination.workoutImageIdArg])
    val workOutInfo: Flow<WorkOut> = getAllWorkOutByID(workOutId)
    init {
        Log.d("TAG", "workOutId workOutId: ${workOutId}")
        Log.d("TAG", "workOutId workOutImageId: ${workOutImageId}")
    }

}