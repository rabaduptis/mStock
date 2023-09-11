package com.root14.mstock.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.root14.mstock.R
import com.root14.mstock.data.adapter.MainRecyclerViewAdapter
import com.root14.mstock.data.converter.ProductConverter
import com.root14.mstock.data.model.ShowCaseDataModel
import com.root14.mstock.databinding.FragmentMainScreenBinding
import com.root14.mstock.viewmodel.MainViewModel

class MainScreenFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        println("hey douglas is that you? ${Firebase.auth.currentUser?.email}")

        mainViewModel.fillRecyclerView()
        mainViewModel.fillRecyclerView.observe(viewLifecycleOwner) {

            binding.recyclerViewModelList.adapter = MainRecyclerViewAdapter(it)
        }

        binding.recyclerViewModelList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        FirebaseAuth.getInstance().signOut()
    }
}