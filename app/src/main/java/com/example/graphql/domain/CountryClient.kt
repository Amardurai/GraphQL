package com.example.graphql.domain

import com.graphql.CountryQuery

interface CountryClient {
    suspend fun getCountries(): List<SimpleCountry>
    suspend fun getDetailedCountry(code: String): DetailedCountry?
}