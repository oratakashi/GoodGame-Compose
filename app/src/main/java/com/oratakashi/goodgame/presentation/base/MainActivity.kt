package com.oratakashi.goodgame.presentation.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.oratakashi.goodgame.presentation.menu.main.MainView
import com.oratakashi.goodgame.presentation.theme.GoodGameTheme
import com.oratakashi.goodgame.presentation.theme.isLight
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoodGameTheme {
                // Remember a SystemUiController
                val systemUiController = rememberSystemUiController()
                val useDarkIcons = MaterialTheme.colorScheme.isLight()
                val backgroundColor = MaterialTheme.colorScheme.background

                SideEffect {
                    // Update all of the system bar colors to be transparent, and use
                    // dark icons if we're in light theme
                    systemUiController.setSystemBarsColor(
                        color = backgroundColor,
                        darkIcons = useDarkIcons
                    )

                    // setStatusBarsColor() and setNavigationBarColor() also exist
                }
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainView()
                }
            }
        }
    }
}

