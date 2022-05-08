package com.oratakashi.goodgame.presentation.menu.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oratakashi.goodgame.domain.PlatformUsecase
import com.oratakashi.goodgame.domain.model.platforms.Platforms
import com.oratakashi.viewbinding.core.tools.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val platform: PlatformUsecase
) : ViewModel() {
    private val _platforms: MutableStateFlow<State<List<Platforms>>> by lazy {
        MutableStateFlow(State.default())
    }
    val platforms: StateFlow<State<List<Platforms>>> = _platforms
    
    private fun getPlatform() {
        platform.getPlatform()
            .onStart { _platforms.emit(State.loading()) }
            .onEach {
                if(it.isNotEmpty()) {
                    _platforms.emit(State.success(it))
                } else {
                    _platforms.emit(State.empty())
                }
            }
            .catch { _platforms.emit(State.fail(it, it.message)) }
            .launchIn(viewModelScope)
    }
    
    init {
        getPlatform()
    }
}