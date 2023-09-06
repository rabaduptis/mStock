package com.root14.mstock.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.root14.mstock.data.dao.ProductDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class BarcodeViewModel @Inject constructor(
    private val productDao: ProductDao
) : ViewModel() {


    //TODO: implement liveData
    fun checkUniqueProduct(ean: String) {
        viewModelScope.launch(Dispatchers.IO) {
            var result = productDao.getProductDaoByEAN(ean)

            if (result != null) {
                //TODO:get data from db
                //TODO:navigate to detailProduct Fragment
            } else {
                //TODO:insert data to db
                //TODO:navigate to addProduct Fragment
            }
        }

    }
}