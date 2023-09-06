package com.root14.mstock.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.root14.mstock.data.entity.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM ProductEntity")
    suspend fun getAllUniqueProduct(): List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE ean IN (:ean)")
    suspend fun getProductDaoByEAN(ean: String): ProductEntity

    @Delete
    suspend fun deleteProduct(productEntity: ProductEntity)

    @Insert
    suspend fun insertProduct(productEntity: ProductEntity)
}