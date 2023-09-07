package com.root14.mstock.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.root14.mstock.data.converter.ProductConverter
import com.root14.mstock.data.dao.ProductDao
import com.root14.mstock.data.enum.ErrorType
import com.root14.mstock.data.model.ProductModel
import com.root14.mstock.data.state.MStockResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class BarcodeViewModel @Inject constructor(
    private val productDao: ProductDao
) : ViewModel() {


    companion object {
        //last checked barcode code
        private lateinit var _lastCode: String

        fun getLastReadedCode() = _lastCode

    }

    //TODO: implement liveData
    private val _barcodeResult = MutableLiveData<MStockResult<ProductModel>>()
    val barcodeResult: LiveData<MStockResult<ProductModel>> = _barcodeResult
    fun checkUniqueProduct(ean: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = productDao.getProductDaoByEAN(ean)

            _lastCode = ean

            if (result != null) {
                //some records found, GO-TO detailProduct screen
                _barcodeResult.postValue(
                    MStockResult.Success(
                        ProductConverter.ProductEntity2UniqueProductModel(
                            result
                        ),
                    )
                )
            } else {
                _barcodeResult.postValue(MStockResult.Failure(ErrorType.DB_GENERAL_ERROR))
            }
        }
    }
}