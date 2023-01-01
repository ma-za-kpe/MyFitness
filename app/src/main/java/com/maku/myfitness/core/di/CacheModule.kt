package com.maku.myfitness.core.di

import android.content.Context
import androidx.room.Room
import com.maku.myfitness.core.data.offline.MyFitnessDatabase
import com.maku.myfitness.core.data.offline.WorkOutCache
import com.maku.myfitness.core.data.offline.WorkOutCacheImpl
import com.maku.myfitness.core.data.offline.dao.WorkOutDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CacheModule {

    @Binds
    abstract fun bindCache(cache: WorkOutCacheImpl): WorkOutCache

    companion object {

        @Provides
        @Singleton
        fun provideDatabase(
            @ApplicationContext context: Context
        ): MyFitnessDatabase {
            return Room.databaseBuilder(
                context,
                MyFitnessDatabase::class.java,
                "fitness.db"
            )
                .createFromAsset("databases/WorkOut.db")
                .build()
        }

        @Provides
        fun provideWorkOutDao(
            myFitnessDatabase: MyFitnessDatabase
        ): WorkOutDao = myFitnessDatabase.workOutDao()

    }
}