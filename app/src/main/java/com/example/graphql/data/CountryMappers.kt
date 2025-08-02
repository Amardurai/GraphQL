package com.example.graphql.data

import com.example.graphql.domain.DetailedCountry
import com.example.graphql.domain.SimpleCountry
import com.graphql.CountriesQuery
import com.graphql.CountryQuery

fun CountryQuery.Country.toDetailedCountry(): DetailedCountry {
    return DetailedCountry(
        name = name,
        code = code,
        emoji = emoji,
        capital = capital,
        languages = languages.map { it.name },
        continent = continent.name
    )
}

fun CountriesQuery.Country.toDetailedCountry(): SimpleCountry {
    return SimpleCountry(
        name = name,
        code = code,
        emoji = emoji,
        capital = capital,
    )
}