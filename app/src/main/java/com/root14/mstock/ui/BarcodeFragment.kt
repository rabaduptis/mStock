package com.root14.mstock.ui

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.view.PreviewView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.mlkit.vision.barcode.common.Barcode
import com.root14.mstock.R
import com.root14.mstock.data.IMStockBarcodeScanner
import com.root14.mstock.data.MStockBarcodeScanner
import com.root14.mstock.data.enum.ErrorType
import com.root14.mstock.data.state.MStockResult
import com.root14.mstock.databinding.FragmentBarcodeBinding
import com.root14.mstock.viewmodel.BarcodeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class BarcodeFragment : Fragment(), LifecycleOwner {
    private var _binding: FragmentBarcodeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var mStockBarcodeScanner: MStockBarcodeScanner

    private val barcodeViewModel: BarcodeViewModel by activityViewModels()

    private var isOk: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBarcodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mStockBarcodeScanner.addLifecycleOwner(this)
        mStockBarcodeScanner.addContext(requireContext())

        mStockBarcodeScanner.addPermission(Manifest.permission.ACCEPT_HANDOVER)
            .requestPermission(requireActivity())

        binding.previewView.implementationMode = PreviewView.ImplementationMode.COMPATIBLE
        mStockBarcodeScanner.build()
        mStockBarcodeScanner.bindToView(binding.previewView)

        binding.buttonReadBarcode.setOnClickListener {

            mStockBarcodeScanner.processPhoto(object : IMStockBarcodeScanner {
                override fun onBarcodeSuccess(barcodes: MutableList<Barcode>) {
                    Snackbar.make(
                        binding.root, barcodes[0].rawValue.toString(), Snackbar.LENGTH_SHORT
                    ).show()

                    //process barcode data
                    barcodeViewModel.checkUniqueProduct(barcodes[0].rawValue.toString())
                    isOk = true
                }

                override fun onBarcodeFailure(barcodeOnFailure: ErrorType, e: Exception) {
                    Snackbar.make(
                        binding.root,
                        "Barcode could not be read. Try it again.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    isOk = true
                }

                override fun onBarcodeComplete(
                    barcodeOnComplete: ErrorType, task: Task<MutableList<Barcode>>
                ) {
                    // Snackbar.make(binding.root, "onBarcodeComplete", Snackbar.LENGTH_SHORT).show()
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        barcodeViewModel.barcodeResult.observe(viewLifecycleOwner) {
            when (it) {
                is MStockResult.Success -> {
                    println("douglas look i'm alive! ${it.data}")
                    //move to detail fragment when success
                    val bundle =
                        bundleOf("readed-ean-code" to BarcodeViewModel.getLastReadedCode())
                    val currentDestination = findNavController().currentDestination
                    if (currentDestination?.id == R.id.barcodeFragment && isOk) {
                        isOk = false
                        findNavController().navigate(
                            R.id.action_barcodeFragment_to_detailProductFragment, bundle
                        )
                    }
                }

                is MStockResult.Failure -> {
                    println(it.error)

                    val bundle =
                        bundleOf("readed-ean-code" to BarcodeViewModel.getLastReadedCode())
                    val currentDestination = findNavController().currentDestination
                    if (currentDestination?.id == R.id.barcodeFragment && isOk) {
                        isOk = false
                        findNavController().navigate(
                            R.id.action_barcodeFragment_to_addProductFragment, bundle
                        )
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        mStockBarcodeScanner.unbind()
    }
}