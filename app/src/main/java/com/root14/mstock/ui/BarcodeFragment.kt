package com.root14.mstock.ui

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.mlkit.vision.barcode.ZoomSuggestionOptions
import com.google.mlkit.vision.barcode.common.Barcode
import com.root14.mstock.data.IMStockBarcodeScanner
import com.root14.mstock.data.MStockBarcodeScanner
import com.root14.mstock.data.enum.ErrorType
import com.root14.mstock.databinding.FragmentBarcodeBinding
import java.lang.Exception


class BarcodeFragment : Fragment() {
    private var _binding: FragmentBarcodeBinding? = null
    private val binding get() = _binding!!

    private lateinit var mStockBarcodeScanner: MStockBarcodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mStockBarcodeScanner=MStockBarcodeScanner()

        mStockBarcodeScanner.addContext(requireContext())
        mStockBarcodeScanner.build()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBarcodeBinding.inflate(inflater, container, false)

        mStockBarcodeScanner.addPermission(Manifest.permission.ACCEPT_HANDOVER)
            .requestPermission(requireActivity())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.previewView.implementationMode = PreviewView.ImplementationMode.COMPATIBLE
        mStockBarcodeScanner.bindToView(binding.previewView)


        binding.buttonReadBarcode.setOnClickListener {

            mStockBarcodeScanner.processPhoto(object : IMStockBarcodeScanner {
                override fun onBarcodeSuccess(barcodes: MutableList<Barcode>) {
                    Snackbar.make(
                        binding.root, barcodes.get(0).rawValue.toString(), Snackbar.LENGTH_SHORT
                    ).show()
                }

                override fun onBarcodeFailure(barcodeOnFailure: ErrorType, e: Exception) {
                    Snackbar.make(
                        binding.root, "onBarcodeFailure", Snackbar.LENGTH_SHORT
                    ).show()
                }

                override fun onBarcodeComplete(
                    barcodeOnComplete: ErrorType, task: Task<MutableList<Barcode>>
                ) {
                    // Snackbar.make(binding.root, "onBarcodeComplete", Snackbar.LENGTH_SHORT).show()
                }
            })
        }

    }
    //TODO: implement unbind method on onDestroy
}