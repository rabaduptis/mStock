package com.root14.mstock.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.root14.mstock.data.converter.RoomTypeConverter
import com.root14.mstock.data.dao.BaseProductDao
import com.root14.mstock.data.dao.UniqueProductDao
import com.root14.mstock.data.entity.BaseProductEntity
import com.root14.mstock.data.entity.UniqueProductEntity

@Database(
    entities = [BaseProductEntity::class, UniqueProductEntity::class],
    exportSchema = false,
    version = 1
)
@TypeConverters(RoomTypeConverter::class)
abstract class MStockDatabase : RoomDatabase() {
    abstract fun getBaseProductDao(): BaseProductDao
    abstract fun getUniqueProductDao(): UniqueProductDao
}