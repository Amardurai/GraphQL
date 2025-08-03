package com.example.graphql.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.graphql.domain.DetailedCountry
import com.example.graphql.domain.SimpleCountry
import com.example.graphql.presentation.CountryState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryListScreen(
    state: CountryState,
    onCountrySelected: (String) -> Unit,
    onDismissCountry: () -> Unit
) {
    val isDialogOpen = state.country != null
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Countries") },
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier.padding(paddingValues)
        ) {
            if (isDialogOpen) {
                CountryDetailDialog(
                    country = state.country,
                    onDismiss = onDismissCountry
                )
            }

            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                state.countries.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No countries found.",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        items(state.countries, key = { it.code }) { country ->
                            CountryItem(
                                country = country,
                                onClick = { onCountrySelected(country.code) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CountryDetailDialog(
    country: DetailedCountry?,
    onDismiss: () -> Unit
) {
    if (country == null) return
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(28.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = country.emoji,
                    style = MaterialTheme.typography.displayMedium
                )
                Text(
                    text = country.name,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "Capital: ${country.capital}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Code: ${country.code}",
                    style = MaterialTheme.typography.bodyLarge
                )
                androidx.compose.material3.Button(
                    onClick = onDismiss,
                    modifier = Modifier.padding(top = 12.dp)
                ) {
                    Text("Close")
                }
            }
        }
    }
}
@Composable
fun CountryItem(
    country: SimpleCountry,
    onClick: () -> Unit
) {
    Surface(
        tonalElevation = 2.dp,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = country.emoji,
                style = MaterialTheme.typography.headlineSmall
            )
            Column {
                Text(
                    text = country.name,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = country.capital.orEmpty(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview
@Composable
fun CountryItemPreview() {
    CountryItem(
        country = SimpleCountry(
            code = "US",
            name = "United States",
            emoji = "🇺🇸",
            capital = "Washington, D.C.",
        ),
        onClick = {}
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewCountryListScreen() {
    CountryListScreen(
        state = CountryState(
            isLoading = false,
            countries = listOf(
                SimpleCountry("US", "United States", "🇺🇸", "Washington, D.C."),
                SimpleCountry("CA", "Canada", "🇨🇦", "Ottawa"),
                SimpleCountry("MX", "Mexico", "🇲🇽", "Mexico City")
            ),
        ),
        onCountrySelected = {},
        onDismissCountry = {}
    )
}