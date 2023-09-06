package com.root14.mstock.data.converter

import com.root14.mstock.data.entity.BaseProductEntity
import com.root14.mstock.data.model.BaseProductModel

class BaseProductConverter {

    companion object {
        fun BaseProductEntity2BaseProductModel(baseProductEntity: BaseProductEntity): BaseProductModel {
            return BaseProductModel(
                name = baseProductEntity.name,
                ean = baseProductEntity.ean,
                quantity = baseProductEntity.quantity,
                desi = baseProductEntity.desi,
                price = baseProductEntity.price,
                id = baseProductEntity.id
            )

        }

        fun BaseProductModel2BaseProductEntity(baseProductModel: BaseProductModel): BaseProductEntity {
            return BaseProductEntity(
                id = baseProductModel.id,
                name = baseProductModel.name,
                ean = baseProductModel.ean,
                quantity = baseProductModel.quantity,
                desi = baseProductModel.desi,
                price = baseProductModel.price
            )
        }

    }


}