package com.oratakashi.goodgame.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.oratakashi.goodgame.JetpackState
import com.oratakashi.viewbinding.core.tools.State
import kotlinx.coroutines.flow.StateFlow

@Composable
fun <T : Any> MultiStateView(
    modifier: Modifier = Modifier,
    state: StateFlow<State<T>>,
    loadingLayout: @Composable (BoxScope.() -> Unit)? = null,
    emptyLayout: @Composable (BoxScope.() -> Unit?)? = null,
    errorLayout: @Composable (BoxScope.(throwable: Throwable?, messages: String?) -> Unit?)? = null,
    content: @Composable BoxScope.(data: T) -> Unit
) {
    Crossfade(
        targetState = state.collectAsState().value,
        modifier = modifier, label = ""
    ) {
        when (val states = it) {
            is State.Default -> Box {

            }
            is State.Success    -> Box {
                content.invoke(this, states.data)
            }
            is State.Empty   -> Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                emptyLayout?.invoke(this)
            }
            is State.Loading -> Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                loadingLayout?.invoke(this)
            }
            is State.Failure -> Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                errorLayout?.invoke(this, states.throwable, states.message)
            }
        }
    }

}

@Composable
fun <T : Any> MultiStateView(
    modifier: Modifier = Modifier,
    state: JetpackState<State<T>>,
    loadingLayout: @Composable (BoxScope.() -> Unit)? = null,
    emptyLayout: @Composable (BoxScope.() -> Unit?)? = null,
    errorLayout: @Composable (BoxScope.(throwable: Throwable?, messages: String?) -> Unit?)? = null,
    content: @Composable BoxScope.(data: T) -> Unit
) {
    when (val states = state.value) {
        is State.Default -> Box(modifier = modifier)
        is State.Success -> Box(modifier = modifier) {
            content.invoke(this, states.data)
        }
        is State.Empty   -> Box(modifier = modifier) {
            emptyLayout?.invoke(this)
        }
        is State.Loading -> Box(
            modifier = modifier
                .fillMaxSize()
        ) {
            loadingLayout?.invoke(this)
        }
        is State.Failure -> Box(modifier = modifier) {
            errorLayout?.invoke(this, states.throwable, states.message)
        }
    }
}
