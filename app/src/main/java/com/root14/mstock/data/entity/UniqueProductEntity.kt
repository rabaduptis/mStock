package com.root14.mstock.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.root14.mstock.data.model.BaseProductModel

@Entity(tableName = "UniqueProductEntity")
data class UniqueProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "baseProductModel") val baseProductModel: BaseProductModel,
    @ColumnInfo(name = "serialNo") val serialNo: String,
    @ColumnInfo(name = "createDate") val createDate: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "uniqueId") val uniqueId: String
)
