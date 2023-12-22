package com.demo.payments.data.config

import com.demo.payments.models.dto.ErrorDto

open class ApiException(
    open val errorMessage: String?,
    open val error: Throwable? = null,
    open val responseBody: ErrorDto? = null
) : Throwable(errorMessage, error)