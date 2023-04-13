package br.com.mobileti.xotedio.home.feature.home.viewmodel

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mobileti.xotedio.data.Resource
import br.com.mobileti.xotedio.home.feature.home.repository.HomeRepository
import br.com.mobileti.xotedio.home.feature.home.state.HomeIntent
import br.com.mobileti.xotedio.home.feature.home.state.HomeUiState
import br.com.mobileti.xotedio.home.feature.mapper.ActivitySuggest
import br.com.mobileti.xotedio.home.feature.mapper.toActivitySuggestList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val intentChannel = Channel<HomeIntent>(Channel.UNLIMITED)

    private val _homeState = MutableStateFlow(HomeUiState(loading = false))
    val homeState: StateFlow<HomeUiState> = _homeState.asStateFlow()

    init {
        handleIntents()
    }

    fun sendIntent(intent: HomeIntent) {
        viewModelScope.launch {
            intentChannel.send(intent)
        }
    }

    private fun handleIntents() {
        intentChannel
            .consumeAsFlow()
            .onEach { intent ->
                when(intent) {
                    is HomeIntent.InsertRandomActivitySuggest -> {
                        insertRandomActivitySuggest(type = intent.type)
                    }
                    is HomeIntent.GetAllRandomActivitySuggest -> {
                        getAllActivitySuggest()
                    }
                    is HomeIntent.UpdateActivitySuggest -> {
                        updateActivitySuggest(activitySuggest = intent.activitySuggest)
                    }
                    is HomeIntent.RemoveActivitySuggest -> {
                        removeActivitySuggest(activitySuggest = intent.activitySuggest)
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun removeActivitySuggest(activitySuggest: ActivitySuggest) {
        viewModelScope.launch {
            homeRepository.removeActivitySuggest(activitySuggest)
            getAllActivitySuggest()
        }
    }

    private fun updateActivitySuggest(activitySuggest: ActivitySuggest) {
        viewModelScope.launch {
            homeRepository.updateActivitySuggest(activitySuggest)
            getAllActivitySuggest()
        }
    }

    private fun getAllActivitySuggest() {
        viewModelScope.launch {
            _homeState.update {
                it.copy(
                    activitySuggestList = homeRepository.getAllActivitySuggest().first()
                )
            }
        }
    }

    private fun insertRandomActivitySuggest(type: String) {
        viewModelScope.launch {
            homeRepository.insertRandomActivitySuggest(type).collect { res ->
                when (res) {
                    is Resource.Success -> {
                        _homeState.update {
                            it.copy(isInserted = true)
                        }
                    }
                    is Resource.Error -> {
                        _homeState.update {
                            it.copy(error = res.error)
                        }
                    }
                    is Resource.Loading -> {
                        _homeState.update {
                            it.copy(loading = res.isLoading)
                        }
                    }
                }
            }
        }
    }

}