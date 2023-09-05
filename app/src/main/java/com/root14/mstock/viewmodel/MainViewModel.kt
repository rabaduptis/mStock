package com.root14.mstock.viewmodel

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import com.root14.mstock.data.dao.BaseProductDao
import com.root14.mstock.data.dao.UniqueProductDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val baseProductDao: BaseProductDao, private val uniqueProductDao: UniqueProductDao
) : ViewModel() {


    fun _DB_DEBUG_() {
        baseProductDao.getAllUniqueProduct()
    }


}