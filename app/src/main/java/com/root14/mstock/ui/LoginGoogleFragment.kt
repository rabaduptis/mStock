package com.root14.mstock.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.root14.mstock.databinding.FragmentLoginGoogleBinding


class LoginGoogleFragment : Fragment() {
    private var _binding: FragmentLoginGoogleBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginGoogleBinding.inflate(inflater, container, false)
        return binding.root
    }
}