package com.root14.mstock.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.root14.mstock.data.entity.UniqueProductEntity
import com.root14.mstock.data.model.UniqueProductModel

@Dao
interface UniqueProductDao {

    @Query("SELECT * FROM UniqueProductEntity")
    fun getAllUniqueProduct(): List<UniqueProductEntity>

    @Query("SELECT * FROM UniqueProductEntity WHERE serialNo IN (:serialNo)")
    fun getUniqueProductDaoBySerialNo(serialNo: String): List<UniqueProductEntity>

    @Delete
    fun deleteUniqueProduct(uniqueProductModel: UniqueProductEntity)

    @Insert
    fun insertUniqueProduct(uniqueProductModel: UniqueProductEntity)
}