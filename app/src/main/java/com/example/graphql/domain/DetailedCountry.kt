package com.example.graphql.domain

data class DetailedCountry(
    val name: String,
    val code: String,
    val emoji: String,
    val capital: String? = null,
    val languages: List<String>,
    val continent: String
)
