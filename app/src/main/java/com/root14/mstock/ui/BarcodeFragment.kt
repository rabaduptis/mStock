package com.root14.mstock.ui

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.snackbar.Snackbar
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.ZoomSuggestionOptions
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.root14.mstock.databinding.FragmentBarcodeBinding
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.UUID
import kotlin.random.Random


class BarcodeFragment : Fragment() {
    private var _binding: FragmentBarcodeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBarcodeBinding.inflate(inflater, container, false)

        if (allPermissionsGranted()) {
            Log.d("TAG", "required perms granted for barcode reader.")
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(), REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }

        return binding.root
    }


    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>

    private val REQUEST_CODE_PERMISSIONS = 10
    private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)

    val imageCapture: ImageCapture = ImageCapture.Builder().build()
    var preview: Preview = Preview.Builder().build()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({

            val cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider)


        }, ContextCompat.getMainExecutor(requireContext()))

        binding.buttonReadBarcode.setOnClickListener {
            takePhoto()
        }

    }
    //TODO: implement unbind method on onDestroy

    private fun takePhoto() {

        imageCapture.takePicture(ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onError(exception: ImageCaptureException) {
                    super.onError(exception)
                    val msg = "Photo capture failed: ${exception.message}"
                    Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
                    Log.e("TAG", msg)
                }

                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)
                    val msg = "Photo capture succeeded"
                    Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
                    Log.d("TAG", msg)

                    val inputImage = InputImage.fromMediaImage(
                        image.image!!,
                        image.imageInfo.rotationDegrees
                    )

                    //TODO:implement auto zoom
                    val options = BarcodeScannerOptions.Builder()
                        .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
                        .enableAllPotentialBarcodes()
                        .build()

                    val scanner = BarcodeScanning.getClient(options)

                    scanner.process(inputImage)
                        .addOnSuccessListener { barcodes ->
                            for (barcode in barcodes) {
                                val barcodeValue = barcode.rawValue
                                Log.d("TAG", "Barcode: $barcodeValue")
                                Snackbar.make(
                                    binding.root,
                                    barcodeValue.toString(),
                                    Snackbar.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }
                        .addOnFailureListener { e ->
                            e.printStackTrace()
                            Snackbar.make(
                                binding.root,
                                "cannot find barcode",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                        .addOnCompleteListener {
                            image.close()
                        }
                }
            })
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    private fun bindPreview(cameraProvider: ProcessCameraProvider) {


        var cameraSelector: CameraSelector =
            CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()

        preview.setSurfaceProvider(binding.previewView.surfaceProvider)


        var camera = cameraProvider.bindToLifecycle(
            this as LifecycleOwner, cameraSelector, preview, imageCapture
        )
    }


}