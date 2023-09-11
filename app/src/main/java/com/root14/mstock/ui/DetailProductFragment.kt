package com.root14.mstock.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.root14.mstock.R
import com.root14.mstock.databinding.FragmentDetailProductBinding
import com.root14.mstock.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailProductFragment : Fragment() {

    private val detailViewModel: DetailViewModel by activityViewModels()

    private var _binding: FragmentDetailProductBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailProductBinding.inflate(inflater, container, false)

        requireActivity().onBackPressedDispatcher.addCallback {
            val currentDestination = findNavController().currentDestination
            if (currentDestination?.id == R.id.barcodeFragment) {
                findNavController().navigate(R.id.action_detailProductFragment_to_mainScreenFragment)
            }
        }

        val readEanCode = arguments?.getString("readed-ean-code")
        detailViewModel.getProductDetail(readEanCode.toString())

        detailViewModel.getUniqueProduct.observe(viewLifecycleOwner) {

            binding.twProductName.text = it.name
            binding.twEan.text = it.ean
            binding.twSerialNo.text = it.quantity
            binding.twDesi.text = it.desi
            binding.twPrice.text = it.price
            binding.twDateOfCreate.text = it.createDate
            binding.twAppOwnId.text = it.uniqueId
        }
        return binding.root
    }
}