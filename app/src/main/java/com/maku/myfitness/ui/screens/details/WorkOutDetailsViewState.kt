package com.maku.myfitness.ui.screens.details

import com.maku.myfitness.core.data.offline.model.WorkOut

data class WorkOutDetailsViewState(
    val loading: Boolean = true,
    val workOuts: List<WorkOut> = emptyList(),
    // Using Event prevents your app from handling the error more than once.
    // val failure: Event<Throwable>? = null
)


