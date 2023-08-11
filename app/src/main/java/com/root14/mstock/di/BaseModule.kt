package com.root14.mstock.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.root14.mstock.database.MStockDatase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BaseModule {

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, MStockDatase::class.java, "mStockDB").build()


    @Singleton
    @Provides
    fun provideBaseProductDao(database: MStockDatase) = database.getBaseProductDao()

    @Singleton
    @Provides
    fun provideUniqueProductDao(database: MStockDatase) = database.getUniqueProductDao()
}