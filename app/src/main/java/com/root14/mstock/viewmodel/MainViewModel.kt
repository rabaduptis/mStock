package com.root14.mstock.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.root14.mstock.data.converter.ProductConverter
import com.root14.mstock.data.dao.ProductDao
import com.root14.mstock.data.model.ProductModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val productDao: ProductDao
) : ViewModel() {

    private val _fillRecyclerView = MutableLiveData<List<ProductModel>>()
    val fillRecyclerView: LiveData<List<ProductModel>> = _fillRecyclerView
    fun fillRecyclerView() {
        viewModelScope.launch(Dispatchers.IO) {
            //uniqueProductDao.insertUniqueProduct(generateDebugUniqueProductEntity())
            val _result = productDao.getAllUniqueProduct()

            if (_result.isNotEmpty()) {
                val result = ProductConverter.UniqueProductEntity2UniqueProductModel(_result)
                _fillRecyclerView.postValue(result)
            }
        }
    }
}