package com.example.graphql.data

import com.apollographql.apollo.ApolloClient
import com.example.graphql.domain.CountryClient
import com.example.graphql.domain.DetailedCountry
import com.example.graphql.domain.SimpleCountry
import com.graphql.CountriesQuery
import com.graphql.CountryQuery

class ApolloCountryClient(private val apolloClient: ApolloClient) : CountryClient {
    override suspend fun getCountries(): List<SimpleCountry> {
        return apolloClient.query(CountriesQuery())
            .execute()
            .data
            ?.countries
            ?.map { it.toDetailedCountry() }
            .orEmpty()
    }

    override suspend fun getDetailedCountry(code: String): DetailedCountry? {
        return apolloClient.query(CountryQuery(code))
            .execute()
            .data
            ?.country
            ?.toDetailedCountry()
    }
}