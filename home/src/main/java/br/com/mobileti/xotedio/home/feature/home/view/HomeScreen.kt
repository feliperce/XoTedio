package br.com.mobileti.xotedio.home.feature.home.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.mobileti.xotedio.design.ui.MarginPaddingSizeMedium
import br.com.mobileti.xotedio.home.R
import br.com.mobileti.xotedio.home.feature.mapper.ActivitySuggest

@Composable
fun HomeScreen() {

}

@Composable
fun HomeContent() {

}

/*
{
  "activity": "Organize a bookshelf",
  "type": "busywork",
  "participants": 1,
  "price": 0,
  "link": "",
  "key": "6098037",
  "accessibility": 0
}
 */

@Composable
fun ActivityItem(activitySuggest: ActivitySuggest) {
    Column {
        Text(
            text = stringResource(
                id = R.string.activity_suggest_activity_title,
                activitySuggest.activity, activitySuggest.participants
            )
        )

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.disabled) {
            Text(text = activitySuggest.type)
        }

        Text(
            modifier = Modifier.padding(top = MarginPaddingSizeMedium),
            text = stringResource(id = R.string.activity_suggest_price_title, activitySuggest.price)
        )
        Text(text = activitySuggest.link)
        Text(text = activitySuggest.key)
    }
}

@Composable
fun ActivityList(activitySuggestList: List<ActivitySuggest>) {

}

@Composable
@Preview
fun ActivityItemPreview() {
    ActivityItem(activitySuggest = fakeActivitySuggest)
}

@Composable
@Preview
fun ActivityListPreview() {
    ActivityList(
        activitySuggestList = fakeActivitySuggestList
    )
}

private val fakeActivitySuggest = ActivitySuggest(
    accessibility = 0.0,
    activity = "Organize a bookshelf",
    key = "6098037",
    link = "http://",
    participants = 0,
    price = 0.0,
    type = "busywork"
)

private val fakeActivitySuggestList = listOf(
    fakeActivitySuggest,
    fakeActivitySuggest,
    fakeActivitySuggest,
    fakeActivitySuggest,
    fakeActivitySuggest
)