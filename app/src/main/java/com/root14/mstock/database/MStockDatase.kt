package com.root14.mstock.database

import androidx.room.RoomDatabase
import com.root14.mstock.data.dao.BaseProductDao
import com.root14.mstock.data.dao.UniqueProductDao

abstract class MStockDatase : RoomDatabase() {
    abstract fun getBaseProductDao(): BaseProductDao
    abstract fun getUniqueProductDao(): UniqueProductDao
}