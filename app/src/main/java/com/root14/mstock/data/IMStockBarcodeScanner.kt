package com.root14.mstock.data

import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.barcode.common.Barcode
import com.root14.mstock.data.enum.ErrorType
import java.lang.Exception

interface IMStockBarcodeScanner {
    fun onBarcodeSuccess(barcodes: MutableList<Barcode>)
    fun onBarcodeFailure(barcodeOnFailure: ErrorType, e: Exception)
    fun onBarcodeComplete(barcodeOnComplete: ErrorType, task: Task<MutableList<Barcode>>)
}