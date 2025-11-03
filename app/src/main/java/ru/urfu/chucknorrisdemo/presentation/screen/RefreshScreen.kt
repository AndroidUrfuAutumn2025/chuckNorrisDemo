package ru.urfu.chucknorrisdemo.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.urfu.chucknorrisdemo.domain.viewmodel.RandomFactViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RefreshScreen() {
    val viewModel: RandomFactViewModel = koinViewModel()
    val randomFact by viewModel.randomFact.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            viewModel.setRefreshing(true)
            viewModel.getRandomFact()
            viewModel.setRefreshing(false)
        },
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (randomFact != null) {
                Text(
                    text = randomFact!!.description,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .weight(1f)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "${randomFact!!.startYear} - ${randomFact!!.endYear}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
            } else {
                Text("Загрузка...")
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    viewModel.getRandomFact()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Refresh, contentDescription = "Обновить")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Новый случайный факт")
            }
        }
    }
}

@Preview
@Composable
fun RefreshScreenPreview() {
    RefreshScreen()
}
