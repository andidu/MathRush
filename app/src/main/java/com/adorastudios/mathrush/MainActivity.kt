@file:OptIn(ExperimentalAnimationApi::class)

package com.adorastudios.mathrush

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.adorastudios.mathrush.domain.RecordRepository
import com.adorastudios.mathrush.presentation.screen_finish.FinishScreen
import com.adorastudios.mathrush.presentation.screen_main.MainScreen
import com.adorastudios.mathrush.presentation.screen_play.PlayScreen
import com.adorastudios.mathrush.presentation.utils.MathRushTheme
import com.adorastudios.mathrush.presentation.utils.Screens
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            MathRushTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val navController = rememberAnimatedNavController()

                    AnimatedNavHost(
                        modifier = Modifier.fillMaxSize(),
                        navController = navController,
                        startDestination = Screens.Main.route,
                    ) {
                        composable(
                            route = Screens.Main.route,
                        ) {
                            MainScreen(navController = navController)
                        }
                        composable(
                            route = Screens.Play.route + "?${Screens.TASK_NAME_PARAMETER}={${Screens.TASK_NAME_PARAMETER}}",
                            arguments = listOf(
                                navArgument(name = Screens.TASK_NAME_PARAMETER) {
                                    type = NavType.StringType
                                    defaultValue = RecordRepository.ADD1 + RecordRepository.TIME
                                },
                            ),
                        ) {
                            PlayScreen(navController = navController)
                        }
                        composable(
                            route = Screens.Result.route +
                                "?${Screens.TASK_NAME_PARAMETER}={${Screens.TASK_NAME_PARAMETER}}" +
                                "&${Screens.TASK_RESULT_PARAMETER}={${Screens.TASK_RESULT_PARAMETER}}" +
                                "&${Screens.TASK_SUCCESS_PARAMETER}={${Screens.TASK_SUCCESS_PARAMETER}}",
                            arguments = listOf(
                                navArgument(name = Screens.TASK_NAME_PARAMETER) {
                                    type = NavType.StringType
                                    defaultValue = RecordRepository.ADD1 + RecordRepository.TIME
                                },
                                navArgument(name = Screens.TASK_RESULT_PARAMETER) {
                                    type = NavType.LongType
                                    defaultValue = -1
                                },
                                navArgument(name = Screens.TASK_SUCCESS_PARAMETER) {
                                    type = NavType.BoolType
                                    defaultValue = false
                                },
                            ),
                        ) {
                            FinishScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MathRushTheme {
        Greeting("Android")
    }
}
