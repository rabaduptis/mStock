package com.root14.mstock.data.state

import com.google.firebase.auth.FirebaseUser

data class LoginState(
    var user: FirebaseUser? = null, var exception: Exception? = null, var success: Boolean? = false
)