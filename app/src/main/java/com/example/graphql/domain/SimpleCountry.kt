package com.example.graphql.domain

data class SimpleCountry(
    val name: String,
    val code: String,
    val emoji: String,
    val capital: String? = null,
)
