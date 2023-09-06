package com.root14.mstock.data.state

import com.root14.mstock.data.enum.ErrorType

sealed class MStockResult<out T> {
    class Success<T>(val data: T) : MStockResult<T>()
    class Failure(val error: ErrorType) : MStockResult<Nothing>()
}
