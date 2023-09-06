package com.root14.mstock.data.enum

enum class ErrorType() {
    LOGIN_EMPTY, PASSWORD_EMPTY,

    /**
     * not formatted properly
     */
    LOGIN_NOT_PROPER, PASSWORD_NOT_PROPER, BARCODE_ON_FAILURE, BARCODE_ON_SUCCESS, BARCODE_ON_COMPLETE,

    /**
     * database errors
     */
    DB_GENERAL_ERROR, DB_CANNOT_FIND, DB_INTEGRITY_ERROR,


}