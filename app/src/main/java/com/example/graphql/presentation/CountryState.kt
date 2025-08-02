package com.example.graphql.presentation

import com.example.graphql.domain.DetailedCountry
import com.example.graphql.domain.SimpleCountry

data class CountryState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val country: DetailedCountry? = null,
    val countries: List<SimpleCountry> = emptyList()
)
