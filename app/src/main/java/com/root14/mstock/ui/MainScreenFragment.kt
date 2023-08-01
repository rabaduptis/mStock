package com.root14.mstock.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.root14.mstock.R
import com.root14.mstock.data.MainRecyclerViewAdapter
import com.root14.mstock.data.model.ShowCaseDataModel
import com.root14.mstock.databinding.FragmentMainScreenBinding

class MainScreenFragment : Fragment() {
    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.extendedFab.setOnClickListener {
            //TODO:implement barcode reader
            findNavController().navigate(R.id.action_mainScreenFragment_to_barcodeFragment)
        }

        //a list with only the types of products
        val models = mutableListOf(
            ShowCaseDataModel("product 1", "12345678912345", 12),
            ShowCaseDataModel("product 2", "12345678912345", 12),
            ShowCaseDataModel("product 3", "12345678912345", 12),
            ShowCaseDataModel("product 4", "12345678912345", 12),
            ShowCaseDataModel("product 5", "12345678912345", 12),
        )

        binding.recyclerViewModelList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.recyclerViewModelList.adapter = MainRecyclerViewAdapter(models)

    }
}