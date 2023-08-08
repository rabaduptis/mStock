package com.root14.mstock.data

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.security.Permission

class MStockBarcodeScanner {

    private val REQUEST_CODE_PERMISSIONS = 10
    private var requiredPermissions = ArrayList<String>()
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private var imageCapture: ImageCapture
    private var preview: Preview
    private lateinit var context: Context
    private var options: BarcodeScannerOptions

    init {
        //default permission for barcodeReader
        requiredPermissions.add(Manifest.permission.CAMERA)

        imageCapture = ImageCapture.Builder().build()
        preview = Preview.Builder().build()

        options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS).enableAllPotentialBarcodes()
            .build()
    }

    public fun processPhoto() {
        imageCapture.takePicture(ContextCompat.getMainExecutor(this.context),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onError(exception: ImageCaptureException) {
                    super.onError(exception)
                    val msg = "Photo capture failed: ${exception.message}"

                    Log.e("TAG", msg)
                }

                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)
                    val msg = "Photo capture succeeded"

                    Log.d("TAG", msg)

                    val inputImage = InputImage.fromMediaImage(
                        image.image!!, image.imageInfo.rotationDegrees
                    )


                    val scanner = BarcodeScanning.getClient(options)

                    scanner.process(inputImage).addOnSuccessListener { barcodes ->
                        for (barcode in barcodes) {
                            val barcodeValue = barcode.rawValue
                            Log.d("TAG", "Barcode: $barcodeValue")
                        }
                    }.addOnFailureListener { e ->
                        e.printStackTrace()
                    }.addOnCompleteListener {
                        image.close()
                    }
                }
            })
    }

    public fun bindToView(previewView: PreviewView) {
        cameraProviderFuture = ProcessCameraProvider.getInstance(this.context)
        cameraProviderFuture.addListener({

            val cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider, previewView)

        }, ContextCompat.getMainExecutor(this.context))
    }


    private fun bindPreview(cameraProvider: ProcessCameraProvider, previewView: PreviewView) {
        val cameraSelector: CameraSelector =
            CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()

        preview.setSurfaceProvider(previewView.surfaceProvider)

        var camera = cameraProvider.bindToLifecycle(
            context as LifecycleOwner, cameraSelector, preview, imageCapture
        )
    }

    public fun addContext(context: Context): MStockBarcodeScanner {
        this.context = context
        return this
    }

    /**
     * builder method
     */
    public fun build(): MStockBarcodeScanner {
        cameraProviderFuture = ProcessCameraProvider.getInstance(this.context)
        return this
    }

    /**
     * add permission to grand
     */
    public fun addPermission(permissions: ArrayList<String>): MStockBarcodeScanner {
        this.requiredPermissions += permissions
        return this
    }

    /**
     * add permission to grand
     */
    public fun addPermission(permission: String): MStockBarcodeScanner {
        this.requiredPermissions.add(permission)
        return this
    }

    /**
     * to grand all needed permission
     */
    public fun requestPermission(activity: Activity) {
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