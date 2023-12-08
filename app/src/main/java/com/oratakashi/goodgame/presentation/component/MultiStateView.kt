package com.oratakashi.goodgame.presentation.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
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
        modifier = modifier, label = "multiStateView"
    ) {
        when (val states = it) {
            is State.Default -> Box {

            }

            is State.Success -> Box {
                content.invoke(this, states.data)
            }

            is State.Empty -> Box(
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


fun <T : Any> LazyStaggeredGridScope.MultiStateView(
    state: LazyPagingItems<T>,
    loadingLayout: @Composable (() -> Unit)? = null,
    errorLayout: @Composable ((throwable: Throwable?, messages: String?) -> Unit)? = null,
    content: @Composable (data: T?) -> Unit
) {
    when (val states = state.loadState.refresh) {
        is LoadState.Loading -> item(
            span = StaggeredGridItemSpan.FullLine
        ) {
            loadingLayout?.invoke()
        }

        is LoadState.Error -> item(
            span = StaggeredGridItemSpan.FullLine
        ) {
            errorLayout?.invoke(states.error, states.error?.message)
        }

        is LoadState.NotLoading -> items(state.itemCount) { index ->
            content.invoke(state[index])
        }
    }
}


