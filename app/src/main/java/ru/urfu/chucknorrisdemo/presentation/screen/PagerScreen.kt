package ru.urfu.chucknorrisdemo.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import org.koin.androidx.compose.koinViewModel
import ru.urfu.chucknorrisdemo.domain.model.FactEntity
import ru.urfu.chucknorrisdemo.domain.viewmodel.GalleryViewModel

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagerScreen() {
    val viewModel: GalleryViewModel = koinViewModel()
    val currentPage by viewModel.currentPage.collectAsState()
    val facts = viewModel.facts

    val pagerState = rememberPagerState()

    LaunchedEffect(pagerState.currentPage) {
        viewModel.updateCurrentPage(pagerState.currentPage)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = facts[currentPage].name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "${facts[currentPage].startYear} - ${facts[currentPage].endYear}",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        HorizontalPager(
            count = facts.size,
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) { page ->
            FactImageItem(fact = facts[page])
        }

        Spacer(modifier = Modifier.height(16.dp))

        DotsIndicator(
            totalDots = facts.size,
            selectedIndex = currentPage,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = facts[currentPage].description,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Justify,
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FactImageItem(fact: FactEntity) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        GlideImage(
            model = fact.imageUrl,
            contentDescription = "Изображение: ${fact.name}",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp))
        ) {
            it.placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_report_image)
        }
    }
}

@Composable
fun DotsIndicator(
    totalDots: Int,
    selectedIndex: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(totalDots) { index ->
            val isSelected = index == selectedIndex
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .padding(6.dp)
            ) {
                androidx.compose.material3.Card(
                    modifier = Modifier.size(if (isSelected) 16.dp else 12.dp)
                        .align(Alignment.Center),
                    shape = CircleShape,
                    colors = androidx.compose.material3.CardDefaults.cardColors(
                        containerColor = if (index == selectedIndex) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        }
                    )
                ) {}
            }
        }
    }
}

@Preview
@Composable
fun PagerScreenPreview() {
    PagerScreen()
}
