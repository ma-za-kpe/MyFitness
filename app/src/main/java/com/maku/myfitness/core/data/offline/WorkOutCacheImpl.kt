package com.maku.myfitness.core.data.offline

import com.maku.myfitness.core.data.offline.dao.WorkOutDao
import com.maku.myfitness.core.data.offline.model.WorkOut
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class WorkOutCacheImpl @Inject constructor(
    private val workOutDao: WorkOutDao,
): WorkOutCache {
    override fun getAllWorkOut(): Flow<List<WorkOut>> {
       return workOutDao.getAllWorkOut()
    }

    override fun getSingleWorkOut(id: Int): Flow<WorkOut> {
       return workOutDao.loadSingleWorkOut(id)
    }
}