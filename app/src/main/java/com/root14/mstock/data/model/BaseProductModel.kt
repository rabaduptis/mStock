package com.root14.mstock.data.model

data class BaseProductModel(
    var name: String,
    var ean: String,
    var quantity: Int,
    var uniqueId: String,
    var desi: String,
    var price: String
)