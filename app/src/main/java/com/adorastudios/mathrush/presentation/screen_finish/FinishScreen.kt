@file:OptIn(ExperimentalAnimationApi::class, ExperimentalLayoutApi::class)

package com.adorastudios.mathrush.presentation.screen_finish

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsIgnoringVisibility
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Replay
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.adorastudios.mathrush.R
import com.adorastudios.mathrush.domain.RecordRepository
import com.adorastudios.mathrush.presentation.screen_main.GameType
import com.adorastudios.mathrush.presentation.screen_play.formatMillisAsTime
import com.adorastudios.mathrush.presentation.utils.DefaultPadding
import com.adorastudios.mathrush.presentation.utils.Screens
import com.adorastudios.mathrush.presentation.utils.SmallerPadding
import java.lang.Long.max

@Composable
fun FinishScreen(
    navController: NavController,
    viewModel: FinishViewModel = hiltViewModel(),
) {
    BackHandler(enabled = true) {
        navController.navigate(Screens.Main.route) {
            popUpTo(Screens.Main.route) {
                inclusive = true
            }
        }
    }

    val topPaddingDp =
        with(LocalDensity.current) {
            WindowInsets.Companion.statusBarsIgnoringVisibility.getTop(this).toDp()
        }

    AnimatedContent(
        modifier = Modifier.fillMaxSize(),
        targetState = viewModel.state.value,
    ) { finishState ->
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            when (finishState) {
                FinishState.Loading -> {}
                is FinishState.Loaded -> {
                    Row(
                        modifier = Modifier
                            .height(64.dp + topPaddingDp)
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .fillMaxWidth()
                            .padding(horizontal = DefaultPadding)
                            .padding(top = topPaddingDp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(text = stringResource(id = finishState.taskData.task.name))
                    }

                    Spacer(modifier = Modifier.height(DefaultPadding))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(2f)
                            .padding(horizontal = DefaultPadding),
                    ) {
                        when (finishState) {
                            is FinishState.Loaded.Failure -> {
                                Text(
                                    text = stringResource(id = R.string.failure),
                                    style = MaterialTheme.typography.displayLarge,
                                )
                            }

                            is FinishState.Loaded.Success -> {
                                ResultSuccess(finishState)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(DefaultPadding))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .width(IntrinsicSize.Min)
                            .weight(1f)
                            .padding(horizontal = DefaultPadding),
                        verticalArrangement = Arrangement.spacedBy(SmallerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        FilledTonalButton(
                            onClick = {
                                navController.navigate(
                                    Screens.toPlayScreen(
                                        finishState.taskData.task.id + when (finishState.taskData.type) {
                                            GameType.Best -> RecordRepository.BEST
                                            GameType.Time -> RecordRepository.TIME
                                        },
                                    ),
                                ) {
                                    popUpTo(Screens.Play.route) {
                                        inclusive = true
                                    }
                                }
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Replay,
                                contentDescription = null,
                            )
                            Spacer(modifier = Modifier.width(SmallerPadding))
                            Text(
                                text = stringResource(id = R.string.replay),
                                maxLines = 1,
                            )
                        }
                        FilledTonalButton(
                            onClick = {
                                navController.navigate(Screens.Main.route) {
                                    popUpTo(Screens.Main.route) {
                                        inclusive = true
                                    }
                                }
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Menu,
                                contentDescription = null,
                            )
                            Spacer(modifier = Modifier.width(SmallerPadding))
                            Text(
                                text = stringResource(id = R.string.menu),
                                maxLines = 1,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(DefaultPadding))
                }
            }
        }
    }
}

@Composable
fun ColumnScope.ResultSuccess(finishState: FinishState.Loaded.Success) {
    when (finishState.taskData.type) {
        GameType.Best -> {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "${finishState.result}",
                style = MaterialTheme.typography.displayLarge,
            )
            if (finishState.result > finishState.prevBest) {
                Spacer(modifier = Modifier.height(DefaultPadding))
                Text(
                    modifier = Modifier.align(Alignment.End),
                    text = stringResource(id = R.string.newBest),
                )
            }
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.best) +
                    "${max(finishState.prevBest, finishState.result)}",
            )
        }

        GameType.Time -> {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = finishState.result.formatMillisAsTime(),
                style = MaterialTheme.typography.displayLarge,
            )
            if (finishState.result < finishState.prevBest || finishState.prevBest == -1L) {
                Spacer(modifier = Modifier.height(DefaultPadding))
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = stringResource(id = R.string.newBest),
                )
            }
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.best) +
                    max(finishState.prevBest, finishState.result)
                        .formatMillisAsTime(),
            )
        }
    }
}
