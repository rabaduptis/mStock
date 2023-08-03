package com.root14.mstock.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.root14.mstock.data.enum.ErrorType
import com.root14.mstock.data.state.LoginFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {


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