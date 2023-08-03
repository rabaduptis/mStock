package com.root14.mstock.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.root14.mstock.data.enum.ErrorType
import com.root14.mstock.data.state.LoginFormState
import com.root14.mstock.data.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private lateinit var auth: FirebaseAuth


    /**
     * login or register
     */
    private val _login = MutableLiveData<LoginState>()
    val login: LiveData<LoginState> = _login
    fun login(email: String, password: String) {
        auth = Firebase.auth

        if (auth.currentUser == null) {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //success
                    _login.postValue(LoginState(user = auth.currentUser, success = true))
                } else {
                    //fail
                    val exceptionCreateUser = task.exception
                    println(exceptionCreateUser?.message)

                    if (exceptionCreateUser?.message.equals("The email address is already in use by another account.")) {
                        auth.signInWithEmailAndPassword(email, password).addOnFailureListener {

                            if (task.isSuccessful) {
                                _login.postValue(
                                    LoginState(
                                        user = auth.currentUser, success = true
                                    )
                                )
                            } else {
                                val exceptionLogin = task.exception
                                _login.postValue(LoginState(exception = exceptionLogin))
                                println(exceptionLogin?.message)
                            }
                        }
                    }

                    _login.postValue(LoginState(exception = exceptionCreateUser))
                }
            }
        } else {
            _login.postValue(LoginState(user = auth.currentUser, success = true))
        }
    }


    /**
     * check login form state
     */

    private val _checkFormState = MutableLiveData<LoginFormState>()
    val checkFormState: LiveData<LoginFormState> = _checkFormState
    fun checkFormState(email: String, password: String) {
        if (isEmailNameValid(email) && isPasswordValid(password)) {
            _checkFormState.postValue(LoginFormState(success = true))
        } else {
            _checkFormState.postValue(LoginFormState(success = false))
        }
    }

    private fun isEmailNameValid(email: String): Boolean {
        return if (email.isBlank() || email.isEmpty()) {
            _checkFormState.value?.errorType = ErrorType.LOGIN_EMPTY
            false
        } else if (!email.contains("@") && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _checkFormState.postValue(LoginFormState(ErrorType.LOGIN_NOT_PROPER))
            false
        } else {
            return true
        }
    }

    private fun isPasswordValid(password: String): Boolean {

        return if (password.isNotBlank() || password.isNotEmpty()) {
            if (password.length > 5) {
                true
            } else {
                _checkFormState.postValue(LoginFormState(ErrorType.PASSWORD_NOT_PROPER))
                false
            }
        } else false
    }
}