package com.root14.mstock.data.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.root14.mstock.data.model.ProductModel

class RoomTypeConverter {
    @TypeConverter
    fun fromBaseProductModel(productModel: ProductModel): String {
        return Gson().toJson(productModel)
    }

    @TypeConverter
    fun toBaseProductModel(productModelString: String): ProductModel {
        return Gson().fromJson(productModelString, ProductModel::class.java)
    }
}