@file:OptIn(ExperimentalLayoutApi::class)

package com.adorastudios.mathrush.presentation.screen_main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsIgnoringVisibility
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.adorastudios.mathrush.R
import com.adorastudios.mathrush.domain.RecordRepository
import com.adorastudios.mathrush.presentation.screen_main.top_bar.MaxTopBarHeight
import com.adorastudios.mathrush.presentation.screen_main.top_bar.MinTopBarHeight
import com.adorastudios.mathrush.presentation.screen_main.top_bar.TopBar
import com.adorastudios.mathrush.presentation.screen_main.top_bar.rememberTopBarState
import com.adorastudios.mathrush.presentation.utils.DefaultPadding
import com.adorastudios.mathrush.presentation.utils.Screens
import com.adorastudios.mathrush.presentation.utils.SmallerPadding
import kotlin.math.min

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    val tolBarHeightRange = with(LocalDensity.current) {
        MinTopBarHeight.roundToPx()..MaxTopBarHeight.roundToPx()
    }

    val topBarState = rememberTopBarState(topBarHeightRange = tolBarHeightRange)
    val scrollState = rememberScrollState()

    topBarState.scrollValue = scrollState.value
    val topBarColorProgress = remember(topBarState.progress) {
        1f - min(topBarState.progress * 5, 1f)
    }

    val topPaddingDp =
        with(LocalDensity.current) {
            WindowInsets.Companion.statusBarsIgnoringVisibility.getTop(this).toDp()
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 8.dp)
                .padding(top = MaxTopBarHeight + topPaddingDp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .heightIn(screenHeight - MinTopBarHeight),
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = DefaultPadding),
                    text = when (viewModel.state.value.gameType) {
                        GameType.Best -> {
                            stringResource(id = R.string.gameTypeBestDescription)
                        }

                        GameType.Time -> {
                            stringResource(id = R.string.gameTypeTimeDescription)
                        }
                    },
                )

                Text(
                    text = stringResource(id = R.string.filter),
                )
                LazyRow(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(SmallerPadding),
                ) {
                    item {
                        GameTypeButton(
                            modifier = Modifier.widthIn(40.dp),
                            text = stringResource(id = R.string.no_filter),
                            onCLick = if (viewModel.state.value.selectedOperation != null) {
                                { viewModel.onEvent(MainEvent.FilterChanged(null)) }
                            } else {
                                null
                            },
                        )
                    }
                    items(items = viewModel.operations) {
                        GameTypeButton(
                            modifier = Modifier.width(40.dp),
                            text = it,
                            onCLick = if (viewModel.state.value.selectedOperation != it) {
                                { viewModel.onEvent(MainEvent.FilterChanged(it)) }
                            } else {
                                null
                            },
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = DefaultPadding),
                    verticalArrangement = Arrangement.spacedBy(DefaultPadding),
                ) {
                    viewModel.state.value.tasks.forEach {
                        GameTile(
                            modifier = Modifier.fillMaxWidth(),
                            gameName = stringResource(id = it.name),
                            taskExample = stringResource(id = it.example),
                            gameRecordTime = it.timeRecord,
                            gameRecordBest = it.bestRecord,
                            gameType = viewModel.state.value.gameType,
                        ) {
                            navController.navigate(
                                Screens.toPlayScreen(
                                    when (viewModel.state.value.gameType) {
                                        GameType.Best -> it.id + RecordRepository.BEST
                                        GameType.Time -> it.id + RecordRepository.TIME
                                    },
                                ),
                            )
                        }
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.primaryContainer.add(
                        MaterialTheme.colorScheme.background,
                        1 - topBarColorProgress,
                    ),
                )
                .statusBarsPadding()
                .height(with(LocalDensity.current) { topBarState.height.toDp() }),
        ) {
            TopBar(
                modifier = Modifier.padding(horizontal = 16.dp),
                progress = topBarState.progress,
            ) {
                Text(
                    modifier = Modifier.padding(vertical = DefaultPadding),
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.displayLarge,
                    textAlign = TextAlign.Center,
                    fontSize = (MaterialTheme.typography.titleLarge.fontSize * (1 - topBarState.progress))
                        .add(MaterialTheme.typography.displayLarge.fontSize * topBarState.progress),
                )
                when (viewModel.state.value.gameType) {
                    GameType.Best -> {
                        GameTypeButton(text = stringResource(id = R.string.gameTypeBest))
                        GameTypeButton(text = stringResource(id = R.string.gameTypeTime)) {
                            viewModel.onEvent(MainEvent.GameTypeChanged(GameType.Time))
                        }
                    }

                    GameType.Time -> {
                        GameTypeButton(text = stringResource(id = R.string.gameTypeBest)) {
                            viewModel.onEvent(MainEvent.GameTypeChanged(GameType.Best))
                        }
                        GameTypeButton(text = stringResource(id = R.string.gameTypeTime))
                    }
                }
            }
        }
    }
}

@Composable
private fun GameTypeButton(
    modifier: Modifier = Modifier,
    text: String,
    onCLick: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (onCLick != null) {
            Text(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .clickable {
                        onCLick()
                    }
                    .padding(SmallerPadding),
                text = text,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(2.dp))
        } else {
            Text(
                modifier = Modifier.padding(SmallerPadding),
                text = text,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
            )

            Box(
                modifier = Modifier
                    .size(height = 2.dp, width = 40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
            )
        }
    }
}

fun Color.add(color: Color, proportion: Float): Color {
    val antiProportion = 1 - proportion
    return copy(
        alpha = alpha * antiProportion + color.alpha * proportion,
        red = red * antiProportion + color.red * proportion,
        green = green * antiProportion + color.green * proportion,
        blue = blue * antiProportion + color.blue * proportion,
    )
}

fun TextUnit.add(textUnit: TextUnit): TextUnit {
    return (this.value + textUnit.value).sp
}
