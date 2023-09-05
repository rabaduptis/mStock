package com.root14.mstock.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.root14.mstock.data.entity.BaseProductEntity
import com.root14.mstock.data.entity.UniqueProductEntity
import com.root14.mstock.data.model.BaseProductModel
import com.root14.mstock.data.model.UniqueProductModel

@Dao
interface BaseProductDao {

    @Query("SELECT * FROM BaseProductEntity")
    fun getAllUniqueProduct(): List<BaseProductModel>

    @Query("SELECT * FROM BaseProductEntity WHERE ean IN (:ean)")
    fun getUniqueProductDaoBySerialNo(ean: String): List<BaseProductModel>

    @Delete
    fun deleteUniqueProduct(baseProductEntity: BaseProductEntity)

    @Insert
    fun insertUniqueProduct(baseProductEntity: BaseProductEntity)

}