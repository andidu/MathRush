@file:OptIn(ExperimentalAnimationApi::class, ExperimentalLayoutApi::class)

package com.adorastudios.mathrush.presentation.screen_play

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsIgnoringVisibility
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EmojiEvents
import androidx.compose.material.icons.rounded.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.adorastudios.mathrush.R
import com.adorastudios.mathrush.presentation.utils.DefaultPadding
import com.adorastudios.mathrush.presentation.utils.Screens
import com.adorastudios.mathrush.presentation.utils.SmallerPadding
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PlayScreen(
    navController: NavController,
    viewModel: PlayViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = true) {
        viewModel.flow.collectLatest {
            when (it) {
                is PlayUiEvent.Finished -> {
                    navController.navigate(Screens.toResultScreen(it.taskId, it.result, it.success))
                }
            }
        }
    }

    val topPaddingDp =
        with(LocalDensity.current) {
            WindowInsets.Companion.statusBarsIgnoringVisibility.getTop(this).toDp()
        }

    AnimatedContent(
        modifier = Modifier.fillMaxSize(),
        targetState = viewModel.state.value.taskData,
    ) { taskData ->
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            if (taskData != null) {
                Row(
                    modifier = Modifier
                        .height(64.dp + topPaddingDp)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .fillMaxWidth()
                        .padding(horizontal = DefaultPadding)
                        .padding(top = topPaddingDp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = stringResource(id = taskData.task.name))
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(imageVector = Icons.Rounded.EmojiEvents, contentDescription = stringResource(id = R.string.points))
                    Text(text = "${viewModel.state.value.correctAmount}")
                    Spacer(modifier = Modifier.width(SmallerPadding))
                    Icon(imageVector = Icons.Rounded.Timer, contentDescription = stringResource(id = R.string.timer))
                    Text(text = viewModel.state.value.time)
                }
                Spacer(modifier = Modifier.height(DefaultPadding))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = DefaultPadding)
                        .weight(1f),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = viewModel.state.value.question.first,
                        style = MaterialTheme.typography.displayLarge,
                        textAlign = TextAlign.Center,
                    )
                }
                Spacer(modifier = Modifier.height(DefaultPadding))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = DefaultPadding)
                        .weight(1f),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = if (viewModel.state.value.minus) {
                            "-${viewModel.state.value.currentAnswer}"
                        } else {
                            viewModel.state.value.currentAnswer
                        },
                        style = MaterialTheme.typography.displayLarge,
                        textAlign = TextAlign.Center,
                    )
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(SmallerPadding),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(SmallerPadding),
                    ) {
                        DeleteButton(number = stringResource(id = R.string.removeAll)) {
                            viewModel.onEvent(PlayEvent.DeleteAll)
                        }
                        DeleteButton(number = stringResource(id = R.string.removeOne)) {
                            viewModel.onEvent(PlayEvent.DeleteOne)
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(SmallerPadding),
                    ) {
                        InputButton("7") {
                            viewModel.onEvent(PlayEvent.Number("7"))
                        }
                        InputButton("8") {
                            viewModel.onEvent(PlayEvent.Number("8"))
                        }
                        InputButton("9") {
                            viewModel.onEvent(PlayEvent.Number("9"))
                        }
                        InputButton("0") {
                            viewModel.onEvent(PlayEvent.Number("0"))
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(SmallerPadding),
                    ) {
                        InputButton("4") {
                            viewModel.onEvent(PlayEvent.Number("4"))
                        }
                        InputButton("5") {
                            viewModel.onEvent(PlayEvent.Number("5"))
                        }
                        InputButton("6") {
                            viewModel.onEvent(PlayEvent.Number("6"))
                        }
                        GoButton(stringResource(id = android.R.string.ok)) {
                            viewModel.onEvent(PlayEvent.Ok)
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(SmallerPadding),
                    ) {
                        InputButton("1") {
                            viewModel.onEvent(PlayEvent.Number("1"))
                        }
                        InputButton("2") {
                            viewModel.onEvent(PlayEvent.Number("2"))
                        }
                        InputButton("3") {
                            viewModel.onEvent(PlayEvent.Number("3"))
                        }
                        if (taskData.task.negativeNumbers) {
                            InputButton("-") {
                                viewModel.onEvent(PlayEvent.Minus)
                            }
                        } else {
                            EmptyButton()
                        }
                    }
                }
                Spacer(modifier = Modifier.height(DefaultPadding))
            }
        }
    }
}

@Composable
private fun DeleteButton(
    number: String,
    action: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(width = 150.dp + SmallerPadding, height = 40.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
            .clickable {
                action()
            },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = number,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@Composable
private fun InputButton(
    number: String,
    action: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(75.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clickable {
                action()
            },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = number,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@Composable
private fun EmptyButton() {
    Box(modifier = Modifier.size(75.dp))
}

@Composable
private fun GoButton(
    number: String,
    action: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(75.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
            .clickable {
                action()
            },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = number,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge,
        )
    }
}
