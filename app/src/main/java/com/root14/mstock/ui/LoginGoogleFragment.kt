package com.root14.mstock.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.root14.mstock.R
import com.root14.mstock.databinding.FragmentLoginGoogleBinding


class LoginGoogleFragment : Fragment() {
    private var _binding: FragmentLoginGoogleBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginGoogleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(view).load(R.drawable.day_google_500)
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
            .into(binding.LoginGoogleIconImageView)


        binding.loginGoogleButton.setOnClickListener {

            Snackbar.make(binding.root, "demo-login", Snackbar.LENGTH_SHORT).show()

            //TODO: implement firebase googleAuth

            binding.root.findNavController()
                .navigate(R.id.action_loginGoogleFragment_to_mainScreenFragment)
        }
    }
}