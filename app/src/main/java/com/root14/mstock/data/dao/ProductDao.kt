package com.root14.mstock.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.root14.mstock.data.entity.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM UniqueProductEntity")
    suspend fun getAllUniqueProduct(): List<ProductEntity>

    @Query("SELECT * FROM UniqueProductEntity WHERE ean IN (:ean)")
    suspend fun getProductDaoByEAN(ean: String): List<ProductEntity>

    @Delete
    suspend fun deleteProduct(uniqueProductModel: ProductEntity)

    @Insert
    suspend fun insertProduct(uniqueProductModel: ProductEntity)
}