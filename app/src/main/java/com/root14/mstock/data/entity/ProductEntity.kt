package com.root14.mstock.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID

@Entity(tableName = "ProductEntity")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "createDate") val createDate: String? = SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(
        Date()
    ).toString(),
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "uniqueId") val uniqueId: String? = UUID.randomUUID().toString(),
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "ean") val ean: String,
    @ColumnInfo(name = "quantity") val quantity: String,
    @ColumnInfo(name = "desi") val desi: String,
    @ColumnInfo(name = "price") val price: String,
)
