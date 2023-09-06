package com.root14.mstock.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.root14.mstock.data.dao.ProductDao
import com.root14.mstock.data.entity.ProductEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val productDao: ProductDao
) : ViewModel() {

    fun addUniqueProduct(productEntity: ProductEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            productDao.insertProduct(productEntity)
        }
    }
}