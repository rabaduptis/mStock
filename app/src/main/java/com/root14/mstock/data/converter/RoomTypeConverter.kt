package com.root14.mstock.data.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.root14.mstock.data.model.BaseProductModel

class RoomTypeConverter {
    @TypeConverter
    fun fromBaseProductModel(baseProductModel: BaseProductModel): String {
        return Gson().toJson(baseProductModel)
    }

    @TypeConverter
    fun toBaseProductModel(baseProductModelString: String): BaseProductModel {
        return Gson().fromJson(baseProductModelString, BaseProductModel::class.java)
    }
}