package com.maku.myfitness.core.data.offline

import androidx.room.Database
import androidx.room.RoomDatabase
import com.maku.myfitness.core.data.offline.dao.WorkOutDao
import com.maku.myfitness.core.data.offline.model.WorkOut

@Database(
    entities = [WorkOut::class],
    version = 1
)
abstract class MyFitnessDatabase : RoomDatabase() {
     abstract fun workOutDao(): WorkOutDao
}