package com.maku.myfitness.ui.domain.usecases

import com.maku.myfitness.core.data.offline.WorkOutCache
import javax.inject.Inject

class GetAllWorkOutByName @Inject constructor(
    private val workOutCache: WorkOutCache // use a repo here, ideally in bigger app
) {
    operator fun invoke(name: String) = workOutCache.loadSingleWorkOutByName(name)
}