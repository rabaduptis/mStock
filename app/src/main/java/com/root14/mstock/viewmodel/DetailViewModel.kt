package com.root14.mstock.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.root14.mstock.data.dao.ProductDao
import com.root14.mstock.data.entity.ProductEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val productDao: ProductDao
) : ViewModel() {


    private var _getUniqueProduct = MutableLiveData<ProductEntity>()
    val getUniqueProduct: LiveData<ProductEntity> = _getUniqueProduct
    fun getProductDetail(ean: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val _result = productDao.getProductDaoByEAN(ean)
            _getUniqueProduct.postValue(_result)
        }
    }
}