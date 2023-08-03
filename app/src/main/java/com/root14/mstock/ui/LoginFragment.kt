package com.root14.mstock.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.root14.mstock.R
import com.root14.mstock.databinding.FragmentLoginBinding
import com.root14.mstock.viewmodel.LoginViewModel


class LoginFragment : Fragment(R.layout.fragment_login) {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(view).load(R.drawable.day_google_500)
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
            .into(binding.LoginGoogleIconImageView)


        binding.buttonLoginRegister.setOnClickListener {
            Snackbar.make(binding.root, "demo-login", Snackbar.LENGTH_SHORT).show()

            loginViewModel.login(
                binding.editTextEmail.text.toString(), binding.editTextPassword.text.toString()
            )


            loginViewModel.login.observe(viewLifecycleOwner) {
                if (it.success == true) {
                    binding.root.findNavController()
                        .navigate(R.id.action_loginGoogleFragment_to_mainScreenFragment)
                } else {
                    Snackbar.make(
                        binding.root, it.exception?.message.toString(), Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }


        binding.editTextEmail.addTextChangedListener {
            loginViewModel.checkFormState(
                binding.editTextEmail.text.toString(), binding.editTextPassword.text.toString()
            )
        }
        binding.editTextPassword.addTextChangedListener {
            loginViewModel.checkFormState(
                binding.editTextEmail.text.toString(), binding.editTextPassword.text.toString()
            )
        }
        loginViewModel.checkFormState.observe(viewLifecycleOwner) {
            binding.buttonLoginRegister.isEnabled = it.success == true
            if (it.errorType != null) {
                println("form state error ${it.errorType.toString()}")
            }

        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (Firebase.auth.currentUser != null) {
            binding.root.findNavController()
                .navigate(R.id.action_loginGoogleFragment_to_mainScreenFragment)
        }

    }
}