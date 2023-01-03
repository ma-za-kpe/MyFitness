package com.maku.myfitness.core.data.offline

import com.maku.myfitness.core.data.offline.model.WorkOut
import kotlinx.coroutines.flow.Flow

interface WorkOutCache {
    fun getAllWorkOut(): Flow<List<WorkOut>>
    fun getSingleWorkOut(id: Int): Flow<WorkOut>
    fun loadSingleWorkOutByName(name: String): Flow<List<WorkOut>>
}