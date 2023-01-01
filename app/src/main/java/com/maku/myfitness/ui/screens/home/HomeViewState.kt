package com.maku.myfitness.ui.screens.home

import com.maku.myfitness.core.data.offline.model.WorkOut

data class HomeViewState(
    val loading: Boolean = true,
    val workOuts: List<WorkOut> = emptyList(),
    // Using Event prevents your app from handling the error more than once.
    // val failure: Event<Throwable>? = null
)

//sealed interface HomeViewState {
//    object Loading : HomeViewState
//
//    data class Interests(
//        val authors: List<WorkOut>,
//    ) : HomeViewState
//
//    object Empty : HomeViewState
//}


