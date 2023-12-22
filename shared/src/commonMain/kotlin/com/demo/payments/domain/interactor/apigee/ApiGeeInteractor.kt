package com.demo.payments.domain.interactor.apigee

import com.demo.payments.data.config.Result
import com.demo.payments.models.dto.AuthenticateResponse
import kotlinx.coroutines.flow.Flow

interface ApiGeeInteractor {
    /**
     * This api gets token from ApiGee
     * */
    suspend operator fun invoke(): Flow<Result<AuthenticateResponse>>
}