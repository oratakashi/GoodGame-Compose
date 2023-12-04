package com.oratakashi.goodgame.presentation.menu.genre

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oratakashi.goodgame.JetpackState
import com.oratakashi.goodgame.domain.GenreUsecase
import com.oratakashi.goodgame.domain.model.games.Games
import com.oratakashi.goodgame.domain.model.genre.Genre
import com.oratakashi.viewbinding.core.tools.State
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlin.random.Random


class GenreViewModel(
    private val genre: GenreUsecase
) : ViewModel() {

    private val _genre: MutableStateFlow<State<List<Genre>>> = MutableStateFlow(State.default())
    val genres: StateFlow<State<List<Genre>>> = _genre

    private val _games: MutableState<MutableList<Pair<Int, MutableStateFlow<State<List<Games>>>>>> =
        mutableStateOf(mutableListOf())
    val games: JetpackState<MutableList<Pair<Int, MutableStateFlow<State<List<Games>>>>>> = _games

    private val _refresh: MutableSharedFlow<Int> = MutableSharedFlow()
    val refresh: SharedFlow<Int> = _refresh

    fun getGamesByGenre(id: Int) {
        val lastData = _games.value
        genre.getGamesByGenre(id)
            .onStart {
                lastData.add(id to MutableStateFlow(State.loading()))
                _games.value = lastData
                _refresh.emit(Random.nextInt())
            }
            .onEach {
                if (it.isNotEmpty()) {
                    lastData.find { data -> data.first == id }?.second?.emit(State.success(it))
                    _games.value = lastData
                    _refresh.emit(Random.nextInt())
                } else {
                    lastData.find { data -> data.first == id }?.second?.emit(State.empty())
                    _games.value = lastData
                    _refresh.emit(Random.nextInt())
                }
            }
            .catch {
                lastData.find { data -> data.first == id }?.second?.emit(
                    State.fail(
                        it,
                        it.message
                    )
                )
                _games.value = lastData
                _refresh.emit(Random.nextInt())
            }.launchIn(viewModelScope)
    }

    private fun getGenre() {
        genre.getGenre()
            .onStart { _genre.emit(State.loading()) }
            .onEach {
                if (it.isNotEmpty()) {
                    _genre.emit(State.success(it))
                } else {
                    _genre.emit(State.empty())
                }
            }
            .catch { _genre.emit(State.fail(it, it.message)) }
            .launchIn(viewModelScope)
    }

    init {
        getGenre()
    }
}