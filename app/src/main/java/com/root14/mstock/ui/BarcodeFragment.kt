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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenResumed
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
import com.root14.mstock.viewmodel.AddProductViewModel
import com.root14.mstock.viewmodel.BarcodeViewModel
import com.root14.mstock.viewmodel.LoginViewModel
import com.root14.mstock.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@AndroidEntryPoint
class BarcodeFragment : Fragment(), LifecycleOwner {
    private var _binding: FragmentBarcodeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var mStockBarcodeScanner: MStockBarcodeScanner

    private val barcodeViewModel: BarcodeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBarcodeBinding.inflate(inflater, container, false)

        mStockBarcodeScanner.addLifecycleOwner(viewLifecycleOwner)
        mStockBarcodeScanner.addContext(requireContext())
        mStockBarcodeScanner.addPermission(Manifest.permission.ACCEPT_HANDOVER)
            .requestPermission(requireActivity())

        binding.previewView.implementationMode = PreviewView.ImplementationMode.COMPATIBLE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //mStockBarcodeScanner.addLifecycleOwner(viewLifecycleOwner)

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
                }

                override fun onBarcodeFailure(barcodeOnFailure: ErrorType, e: Exception) {
                    Snackbar.make(
                        binding.root,
                        "Barcode could not be read. Try it again.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                override fun onBarcodeComplete(
                    barcodeOnComplete: ErrorType, task: Task<MutableList<Barcode>>
                ) {
                    // Snackbar.make(binding.root, "onBarcodeComplete", Snackbar.LENGTH_SHORT).show()
                }
            })
        }

        barcodeViewModel.barcodeResult.observe(viewLifecycleOwner) {
            when (it) {
                is MStockResult.Success -> {
                    println("douglas look i'm alive! ${it.data}")
                    //move to detail fragment when success
                    findNavController().navigate(R.id.action_barcodeFragment_to_detailProductFragment)
                }

                is MStockResult.Failure -> {
                    println(it.error)

                    val bundle = bundleOf("readed-ean-code" to BarcodeViewModel.getLastReadedCode())
                    findNavController().navigate(
                        R.id.action_barcodeFragment_to_addProductFragment, bundle
                    )
                }
            }
        }
    }



    override fun onResume() {
        super.onResume()
        mStockBarcodeScanner.bindToView(binding.previewView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mStockBarcodeScanner.unbind()

    }
}