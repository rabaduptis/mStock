package com.root14.mstock.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.root14.mstock.data.converter.RoomTypeConverter
import com.root14.mstock.data.dao.ProductDao
import com.root14.mstock.data.entity.ProductEntity

@Database(
    entities = [ProductEntity::class],
    exportSchema = false,
    version = 1
)
@TypeConverters(RoomTypeConverter::class)
abstract class MStockDatabase : RoomDatabase() {
    abstract fun getProductDao(): ProductDao
}