package com.root14.mstock.di

import android.content.Context
import androidx.room.Room
import com.root14.mstock.database.MStockDatabase
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
        Room.databaseBuilder(context, MStockDatabase::class.java, "mStockDB").build()


    @Singleton
    @Provides
    fun provideProductDao(database: MStockDatabase) = database.getProductDao()
}