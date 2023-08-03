package com.root14.mstock.data.enum

enum class ErrorType() {
    LOGIN_EMPTY, PASSWORD_EMPTY,

    /**
     * not formatted properly
     */
    LOGIN_NOT_PROPER, PASSWORD_NOT_PROPER
}