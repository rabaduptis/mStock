package com.root14.mstock.data.model

data class ProductModel(
    val id: Int?,
    val createDate: String,
    val description: String,
    val uniqueId: String,
    val name: String,
    val ean: String,
    val quantity: Int,
    val desi: String,
    val price: String,
)
