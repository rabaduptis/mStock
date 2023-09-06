package com.root14.mstock.data.converter

import com.root14.mstock.data.entity.UniqueProductEntity
import com.root14.mstock.data.model.UniqueProductModel

class UniqueProductConverter {
    companion object {
        fun UniqueProductEntity2UniqueProductModel(uniqueProductEntity: UniqueProductEntity): UniqueProductModel {
            return UniqueProductModel(
                baseProductModel = uniqueProductEntity.baseProductModel,
                serialNo = uniqueProductEntity.serialNo,
                createDate = uniqueProductEntity.createDate,
                description = uniqueProductEntity.description,
                uniqueId = uniqueProductEntity.uniqueId,
                id = uniqueProductEntity.id
            )
        }

        fun UniqueProductModel2UniqueProductEntity(uniqueProductModel: UniqueProductModel): UniqueProductEntity {
            return UniqueProductEntity(
                id = uniqueProductModel.id,
                baseProductModel = uniqueProductModel.baseProductModel,
                serialNo = uniqueProductModel.serialNo,
                createDate = uniqueProductModel.createDate,
                description = uniqueProductModel.description,
                uniqueId = uniqueProductModel.uniqueId
            )
        }

    }
}