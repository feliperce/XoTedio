package br.com.mobileti.xotedio.home.feature.home.state

import br.com.mobileti.xotedio.data.ErrorType
import br.com.mobileti.xotedio.home.feature.mapper.ActivitySuggest

data class HomeUiState(
    val loading: Boolean = false,
    val error: ErrorType? = null,
    val activitySuggestList: List<ActivitySuggest> = arrayListOf(),
    val isInserted: Boolean = false
)

sealed class HomeIntent {
    class InsertRandomActivitySuggest(val type: String): HomeIntent()
    object GetAllRandomActivitySuggest: HomeIntent()
}