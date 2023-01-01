package com.maku.myfitness.core.data.offline.dao

import androidx.room.Dao
import androidx.room.Query
import com.maku.myfitness.core.data.offline.model.WorkOut
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkOutDao {
    @Query("SELECT * FROM workout")
    fun getAllWorkOut(): Flow<List<WorkOut>>

    @Query("SELECT * FROM workout WHERE ID=:id ")
    fun loadSingleWorkOut(id: Int): Flow<WorkOut>
}