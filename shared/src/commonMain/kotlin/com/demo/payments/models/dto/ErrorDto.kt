package com.demo.payments.models.dto

data class ErrorDto(val timestamp: String,
                    val status: String,
                    val title: String,
                    val type: String,
                    val detail: String,
                    val instance: String,
                    val fault: String)
