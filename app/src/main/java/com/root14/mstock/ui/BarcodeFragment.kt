package com.root14.mstock.ui

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.root14.mstock.data.MStockBarcodeScanner
import com.root14.mstock.databinding.FragmentBarcodeBinding


class BarcodeFragment : Fragment() {
    private var _binding: FragmentBarcodeBinding? = null
    private val binding get() = _binding!!

    private val mStockBarcodeScanner = MStockBarcodeScanner()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        mStockBarcodeScanner.bindToView(binding.previewView)


        binding.buttonReadBarcode.setOnClickListener {
            mStockBarcodeScanner.processPhoto()
        }

    }
    //TODO: implement unbind method on onDestroy
}