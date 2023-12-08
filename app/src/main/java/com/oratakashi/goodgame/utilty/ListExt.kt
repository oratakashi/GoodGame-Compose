package com.oratakashi.goodgame.utilty

import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

fun LazyPagingItems<*>.isRefreshing(): Boolean = loadState.refresh is LoadState.Loading

fun LazyPagingItems<*>.hasItems(): Boolean = itemCount > 0

fun LazyPagingItems<*>.isError(): Pair<Boolean, LoadState.Error?> {
    return if (loadState.refresh is LoadState.Error) {
        true to loadState.refresh as LoadState.Error
    } else {
        false to null
    }
}

fun Iterable<LazyPagingItems<*>>.refreshAll() {
    return forEach { it.refresh() }
}