package com.oratakashi.goodgame.presentation.navigation

import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph(start = true)
@NavGraph
annotation class MainNavGraph(
    val start: Boolean = false
)
