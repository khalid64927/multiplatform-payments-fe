package com.demo.payments.models.dto

enum class RemoteExceptionType {
    SERVER_ERROR,
    CLIENT_ERROR,
    PARSING_ERROR,
    UNKNOWN,
}

class RemoteException(val type: RemoteExceptionType) : Exception()
