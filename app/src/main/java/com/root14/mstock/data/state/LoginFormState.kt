package com.root14.mstock.data.state

import com.root14.mstock.data.enum.ErrorType

data class LoginFormState(
    var errorType: ErrorType? = null, var success: Boolean? = false
)
