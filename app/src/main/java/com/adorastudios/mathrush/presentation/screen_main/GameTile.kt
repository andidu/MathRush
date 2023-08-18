package com.adorastudios.mathrush.presentation.screen_main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.adorastudios.mathrush.R
import com.adorastudios.mathrush.presentation.screen_play.formatMillisAsTime
import com.adorastudios.mathrush.presentation.utils.DefaultPadding
import com.adorastudios.mathrush.presentation.utils.SmallerPadding

@Composable
fun GameTile(
    modifier: Modifier = Modifier,
    gameName: String,
    gameType: GameType,
    taskExample: String,
    gameRecordTime: Long,
    gameRecordBest: Long,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            FilledTonalButton(
                modifier = Modifier.weight(4f),
                onClick = onClick,
            ) {
                Text(
                    text = gameName,
                    textAlign = TextAlign.Center,
                )
            }
            Spacer(modifier = Modifier.width(DefaultPadding))
            val gameRecordTimeText = remember(gameRecordTime) {
                if (gameRecordTime != -1L) {
                    gameRecordTime.formatMillisAsTime()
                } else {
                    "-"
                }
            }
            when (gameType) {
                GameType.Best -> {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = if (gameRecordBest != -1L) "$gameRecordBest" else "-",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Spacer(modifier = Modifier.width(DefaultPadding))
                    Text(
                        modifier = Modifier.weight(1f),
                        text = gameRecordTimeText,
                        textAlign = TextAlign.Center,
                    )
                }

                GameType.Time -> {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = if (gameRecordBest != -1L) "$gameRecordBest" else "-",
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.width(DefaultPadding))
                    Text(
                        modifier = Modifier.weight(1f),
                        text = gameRecordTimeText,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(SmallerPadding))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(id = R.string.taskExample),
            )
            Text(
                modifier = Modifier.weight(1f),
                text = taskExample,
            )
        }
    }
}
