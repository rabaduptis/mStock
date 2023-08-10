package com.root14.mstock.data

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.core.impl.ImageCaptureConfig
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.root14.mstock.data.enum.ErrorType

class MStockBarcodeScanner {

    private val REQUEST_CODE_PERMISSIONS = 10
    private var requiredPermissions = ArrayList<String>()
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private var imageCapture: ImageCapture
    private var preview: Preview
    private lateinit var context: Context
    private var options: BarcodeScannerOptions
    private lateinit var cameraProvider: ProcessCameraProvider

    init {
        //default permission for barcodeReader
        requiredPermissions.add(Manifest.permission.CAMERA)

        imageCapture = ImageCapture.Builder().setTargetResolution(Size(400, 400)).build()
        preview = Preview.Builder().build()

        //TODO: implement auto-zoom according to ml-kit
        options = BarcodeScannerOptions.Builder().setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
            .enableAllPotentialBarcodes().build()
    }

    /**
     * process binded to surface view.
     *@param imStockBarcodeScanner listener interface
     */
    public fun processPhoto(imStockBarcodeScanner: IMStockBarcodeScanner) {
        imageCapture.takePicture(ContextCompat.getMainExecutor(this.context),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onError(exception: ImageCaptureException) {
                    super.onError(exception)
                    val msg = "Photo capture failed: ${exception.message}"

                    Log.e("TAG", msg)
                }

                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)
                    Log.d("MStockBarcodeScanner", "Photo capture succeeded.")

                    val inputImage = InputImage.fromMediaImage(
                        image.image!!, image.imageInfo.rotationDegrees
                    )

                    val scanner = BarcodeScanning.getClient(options)
                    scanner.process(inputImage).addOnSuccessListener { barcodes ->
                        for (barcode in barcodes) {
                            val barcodeValue = barcode.rawValue
                            Log.d("MStockBarcodeScanner", "Barcode: $barcodeValue")
                        }

                        if (!barcodes.isNullOrEmpty()) {
                            imStockBarcodeScanner.onBarcodeSuccess(barcodes)
                            Log.d("MStockBarcodeScanner", "Barcode scan success.")
                        }


                    }.addOnFailureListener { e ->
                        imStockBarcodeScanner.onBarcodeFailure(ErrorType.BARCODE_ON_FAILURE, e)
                        Log.d("MStockBarcodeScanner", e.stackTrace.toString())
                    }.addOnCompleteListener { barcodes ->
                        if (!barcodes.result.isNullOrEmpty()) {
                            imStockBarcodeScanner.onBarcodeComplete(
                                ErrorType.BARCODE_ON_COMPLETE, barcodes
                            )
                            image.close()
                            Log.d("MStockBarcodeScanner", "Barcode scan completed.")
                        }


                    }
                }
            })
    }

    /**
     * to unbind camera
     */
    fun unbind() {
        cameraProvider.unbind()
    }

    /**
     * @param previewView surface to bind
     */
    fun bindToView(previewView: PreviewView) {
        cameraProviderFuture = ProcessCameraProvider.getInstance(this.context)
        cameraProviderFuture.addListener({

            cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider, previewView)

        }, ContextCompat.getMainExecutor(this.context))
    }


    /**
     * @param cameraProvider to bind views
     * @param previewView surface to bind
     */
    private fun bindPreview(cameraProvider: ProcessCameraProvider, previewView: PreviewView) {
        val cameraSelector: CameraSelector =
            CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()

        preview.setSurfaceProvider(previewView.surfaceProvider)

        var camera = cameraProvider.bindToLifecycle(
            context as LifecycleOwner, cameraSelector, preview, imageCapture
        )
    }

    /**
     * @param context add context
     */
    fun addContext(context: Context): MStockBarcodeScanner {
        this.context = context
        return this
    }

    /**
     * builder method
     */
    fun build(): MStockBarcodeScanner {
        cameraProviderFuture = ProcessCameraProvider.getInstance(this.context)
        return this
    }

    /**
     * @param permissions list to add permission to grand
     */
    public fun addPermission(permissions: ArrayList<String>): MStockBarcodeScanner {
        this.requiredPermissions += permissions
        return this
    }

    /**
     * @param permission to add permission to grand
     */
    fun addPermission(permission: String): MStockBarcodeScanner {
        this.requiredPermissions.add(permission)
        return this
    }

    /**
     * @param activity to grand all needed permission
     */
    fun requestPermission(activity: Activity) {
        if (allPermissionsGranted(context)) {
            Log.d("MStockBarcodeScanner", "All permission granted.")
        } else {
            ActivityCompat.requestPermissions(
                activity, requiredPermissions.toTypedArray(), REQUEST_CODE_PERMISSIONS
            )
            Log.e(
                "MStockBarcodeScanner", "MStockBarcodeScanner: requested permission cannot granted."
            )
            //throw Exception("requested permission: $requiredPermissions")
        }
    }

    private fun allPermissionsGranted(context: Context) = requiredPermissions.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }


}