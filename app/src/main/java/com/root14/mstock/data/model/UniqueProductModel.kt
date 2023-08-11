package com.root14.mstock.data.model

data class UniqueProductModel(
    var baseProductModel: BaseProductModel?,
    var serialNo: String,
    var createDate: String,
    var description: String,
    var uniqueId: String,
)
