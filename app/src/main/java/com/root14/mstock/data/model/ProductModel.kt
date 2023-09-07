package com.root14.mstock.data.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID

data class ProductModel(
    val id: Int?,
    val createDate: String = SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(Date()).toString(),
    val description: String,
    val uniqueId: String = UUID.randomUUID().toString(),
    val name: String,
    val ean: String,
    val quantity: String,
    val desi: String,
    val price: String,
)
