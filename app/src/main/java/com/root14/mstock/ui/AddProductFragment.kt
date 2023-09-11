package com.root14.mstock.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.root14.mstock.R
import com.root14.mstock.data.MStockBarcodeScanner
import com.root14.mstock.data.entity.ProductEntity
import com.root14.mstock.databinding.FragmentAddProductBinding
import com.root14.mstock.databinding.FragmentBarcodeBinding
import com.root14.mstock.viewmodel.AddProductViewModel
import com.root14.mstock.viewmodel.BarcodeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddProductFragment : Fragment(){

    private val addProductViewModel: AddProductViewModel by activityViewModels()

    @Inject
    lateinit var mStockBarcodeScanner: MStockBarcodeScanner

    private var _binding: FragmentAddProductBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddProductBinding.inflate(inflater, container, false)

        binding.editTextEan.setText(arguments?.getString("readed-ean-code"))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.buttonAdd.setOnClickListener {

            val productEntity =
                ProductEntity(
                    id = null,
                    createDate = null,
                    binding.editTextDescription.text.toString(),
                    uniqueId = null,
                    binding.editTextProductName.text.toString(),
                    binding.editTextEan.text.toString(),
                    binding.editTextQuantity.text.toString(),
                    binding.editTextDesi.text.toString(),
                    binding.editTextPrice.text.toString()
                )

            addProductViewModel.addUniqueProduct(productEntity)

            Snackbar.make(binding.root, "product saving...", Snackbar.LENGTH_SHORT).show()

            addProductViewModel.addUniqueProductResult.observe(viewLifecycleOwner) {
                Snackbar.make(binding.root, "product saved.", Snackbar.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_addProductFragment_to_mainScreenFragment)
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }
}