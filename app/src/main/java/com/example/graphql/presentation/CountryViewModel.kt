package com.example.graphql.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graphql.domain.CountryClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(private val countryClient: CountryClient):ViewModel() {
    private val _uiState = MutableStateFlow(CountryState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            _uiState.update {
                it.copy(countries = countryClient.getCountries(), isLoading = false)
            }
        }
    }

    fun selectCountry(code: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            _uiState.update {
                it.copy(
                    country = countryClient.getDetailedCountry(code),
                    isLoading = false
                )
            }
        }
    }

    fun dismissCountry() {
        _uiState.update { it.copy(country = null) }
    }

}