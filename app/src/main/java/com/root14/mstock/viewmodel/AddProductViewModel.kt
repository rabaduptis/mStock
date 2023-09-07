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
class AddProductViewModel @Inject constructor(
    private val productDao: ProductDao
) : ViewModel() {


    private val _addUniqueProductResult = MutableLiveData<Boolean>()
    val addUniqueProductResult: LiveData<Boolean> = _addUniqueProductResult
    fun addUniqueProduct(productEntity: ProductEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val _result = productDao.insertProduct(productEntity)
                _addUniqueProductResult.postValue(true)
            } catch (exception: Exception) {
                println("an error happend")
                _addUniqueProductResult.postValue(false)
            }
        }
    }
}