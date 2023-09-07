package com.root14.mstock.data.converter

import com.root14.mstock.data.entity.ProductEntity
import com.root14.mstock.data.model.ProductModel

class ProductConverter {
    companion object {
        fun ProductEntity2UniqueProductModel(productEntity: ProductEntity): ProductModel {
            return ProductModel(
                createDate = productEntity.createDate.toString(),
                description = productEntity.description,
                uniqueId = productEntity.uniqueId.toString(),
                id = productEntity.id,
                desi = productEntity.desi,
                ean = productEntity.ean,
                name = productEntity.name,
                price = productEntity.price,
                quantity = productEntity.quantity
            )
        }

        fun ProductEntity2UniqueProductModel(productEntityList: List<ProductEntity>): List<ProductModel> {
            val productModels = mutableListOf<ProductModel>()

            for (uniqueProductEntity in productEntityList) {
                productModels.add(
                    ProductModel(
                        createDate = uniqueProductEntity.createDate.toString(),
                        description = uniqueProductEntity.description,
                        uniqueId = uniqueProductEntity.uniqueId.toString(),
                        id = uniqueProductEntity.id,
                        desi = uniqueProductEntity.desi,
                        ean = uniqueProductEntity.ean,
                        name = uniqueProductEntity.name,
                        price = uniqueProductEntity.price,
                        quantity = uniqueProductEntity.quantity
                    )
                )
            }

            return productModels
        }


        fun UniqueProductModel2UniqueProductEntity(productModel: ProductModel): ProductEntity {
            return ProductEntity(
                createDate = productModel.createDate,
                description = productModel.description,
                uniqueId = productModel.uniqueId,
                id = productModel.id,
                desi = productModel.desi,
                ean = productModel.ean,
                name = productModel.name,
                price = productModel.price,
                quantity = productModel.quantity
            )
        }

    }
}