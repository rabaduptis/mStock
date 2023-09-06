package com.root14.mstock.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.root14.mstock.data.entity.BaseProductEntity
import com.root14.mstock.data.model.BaseProductModel

@Dao
interface BaseProductDao {

    @Query("SELECT * FROM BaseProductEntity")
    suspend fun getAllUniqueProduct(): List<BaseProductEntity>

    @Query("SELECT * FROM BaseProductEntity WHERE ean IN (:ean)")
    suspend fun getUniqueProductDaoBySerialNo(ean: String): List<BaseProductEntity>

    @Delete
    suspend fun deleteUniqueProduct(baseProductModel: BaseProductEntity)

    @Insert
    suspend fun insertUniqueProduct(baseProductModel: BaseProductEntity)
}