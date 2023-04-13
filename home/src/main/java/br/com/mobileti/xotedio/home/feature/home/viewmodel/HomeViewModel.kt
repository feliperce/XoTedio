package br.com.mobileti.xotedio.home.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mobileti.xotedio.data.Resource
import br.com.mobileti.xotedio.home.feature.home.repository.ActivitySuggestRepository
import br.com.mobileti.xotedio.home.feature.home.state.HomeIntent
import br.com.mobileti.xotedio.home.feature.home.state.HomeUiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val activitySuggestRepository: ActivitySuggestRepository
) : ViewModel() {

    private val intentChannel = Channel<HomeIntent>(Channel.UNLIMITED)

    private val _transactionState = MutableStateFlow(HomeUiState(loading = false))
    val transactionState: StateFlow<HomeUiState> = _transactionState.asStateFlow()

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

                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun insertRandomActivitySuggest(type: String) {
        viewModelScope.launch {
            activitySuggestRepository.insertRandomActivitySuggest(type).collect { res ->
                when (res) {
                    is Resource.Success -> {
                        _transactionState.update {
                            it.copy(isInserted = true)
                        }
                    }
                    is Resource.Error -> {
                        _transactionState.update {
                            it.copy(error = res.error)
                        }
                    }
                    is Resource.Loading -> {
                        _transactionState.update {
                            it.copy(loading = res.isLoading)
                        }
                    }
                }
            }
        }
    }

}