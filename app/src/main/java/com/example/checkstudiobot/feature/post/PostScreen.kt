package com.example.checkstudiobot.feature.post

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.checkstudiobot.R
import com.example.checkstudiobot.core.data.model.Review
import com.example.checkstudiobot.core.util.generateRandomString

@Composable
fun PostContent(
    onPostButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PostViewModel = hiltViewModel(),
) {
    val postState = viewModel.postState.collectAsStateWithLifecycle()
    val loginState = viewModel.loginState.collectAsStateWithLifecycle()

    val googleSignInLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartIntentSenderForResult()) {
            viewModel.onResultSignIn(it)
        }

    LaunchedEffect(key1 = Unit) {
        viewModel.onSignIn(googleSignInLauncher)
    }

    PostScreen(
        onPostButtonClicked = { review ->
            if (loginState.value == LoginState.LoggedIn) {
                viewModel.onPostButtonClicked(review)
                if (postState.value == PostState.Posted) {
                    onPostButtonClicked()
                }
            } else {
                viewModel.onSignIn(googleSignInLauncher)
            }
        },
        modifier = modifier,
    )
}

// Use Studio bot and GitHub Copilot to write this file's code.
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PostScreen(
    onPostButtonClicked: (Review) -> Unit,
    modifier: Modifier = Modifier,
) {
    val point = rememberSaveable { mutableFloatStateOf(1f) }
    val shop = rememberSaveable { mutableStateOf("") }
    val title = rememberSaveable { mutableStateOf("") }
    val content = rememberSaveable { mutableStateOf("") }
    val images = rememberSaveable { mutableStateOf(listOf<Uri>()) }

    val imageLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickMultipleVisualMedia()) {
            images.value = it
        }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        OutlinedTextField(
            value = title.value,
            onValueChange = {
                title.value = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.shop_name)) },
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Text(text = stringResource(id = R.string.point))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = point.floatValue.toString())
        }

        Spacer(modifier = Modifier.height(8.dp))

        Slider(
            value = point.floatValue,
            onValueChange = {
                point.floatValue = Math.round(it * 10.0) / 10.0f
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            valueRange = 1f..5f,
            steps = 40,
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor = MaterialTheme.colorScheme.primary,
                inactiveTrackColor = MaterialTheme.colorScheme.primary,
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = title.value,
            onValueChange = {
                title.value = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.review_title)) },
            maxLines = 1,
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = content.value,
            onValueChange = {
                content.value = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            label = { Text(text = stringResource(id = R.string.review_content)) },
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (images.value.isNotEmpty()) {
            HorizontalPager(state = rememberPagerState(
                initialPage = 0,
                initialPageOffsetFraction = 0f,
                pageCount = { images.value.size }
            )) {
                AsyncImage(
                    images.value[it],
                    contentDescription = null
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { imageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = stringResource(id = R.string.add_image))
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                onPostButtonClicked(
                    Review(
                        id = generateRandomString(24),
                        shopName = shop.value,
                        reviewTitle = title.value,
                        reviewBody = content.value,
                        reviewPoint = point.floatValue,
                        imageUrls = images.value.map { it.toString() } // Fix this code
                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = stringResource(id = R.string.post_review))
        }
    }
}

@Preview
@Composable
private fun PostScreenPreview() {
    PostScreen(
        onPostButtonClicked = {},
    )
}
