package com.root14.mstock.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.root14.mstock.data.converter.BaseProductConverter
import com.root14.mstock.data.converter.UniqueProductConverter
import com.root14.mstock.data.dao.BaseProductDao
import com.root14.mstock.data.dao.UniqueProductDao
import com.root14.mstock.data.entity.UniqueProductEntity
import com.root14.mstock.data.model.BaseProductModel
import com.root14.mstock.data.model.UniqueProductModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val baseProductDao: BaseProductDao, private val uniqueProductDao: UniqueProductDao
) : ViewModel() {


    fun addUniqueProduct(uniqueProductEntity: UniqueProductEntity) {
        viewModelScope.launch {

            uniqueProductDao.insertUniqueProduct(uniqueProductEntity)
        }
    }

    fun generateDebugUniqueProductEntity(): UniqueProductEntity {
        val _base = BaseProductModel(
            desi = "1kg", ean = "12345678", name = "dum name", price = "₺ 14", quantity = 12, id = 1
        )

        return UniqueProductConverter.UniqueProductModel2UniqueProductEntity(
            UniqueProductModel(
                baseProductModel = _base,
                uniqueId = UUID.randomUUID().toString(),
                createDate = "12/12/2012",
                description = "lorem ipsum dolar sit amet.",
                serialNo = "12345678",
                id = 1
            )
        )
    }
}