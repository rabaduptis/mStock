package com.root14.mstock.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UniqueProductEntity")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "createDate") val createDate: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "uniqueId") val uniqueId: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "ean") val ean: String,
    @ColumnInfo(name = "quantity") val quantity: Int,
    @ColumnInfo(name = "desi") val desi: String,
    @ColumnInfo(name = "price") val price: String,
)
