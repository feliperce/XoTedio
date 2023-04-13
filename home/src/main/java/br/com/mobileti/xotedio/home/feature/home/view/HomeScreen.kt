package br.com.mobileti.xotedio.home.feature.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.mobileti.xotedio.data.remote.ActivityStatus
import br.com.mobileti.xotedio.data.remote.ActivitySuggestType
import br.com.mobileti.xotedio.design.components.DefaultAppBar
import br.com.mobileti.xotedio.design.ui.MarginPaddingSizeMedium
import br.com.mobileti.xotedio.design.ui.Pink40
import br.com.mobileti.xotedio.home.R
import br.com.mobileti.xotedio.home.feature.mapper.ActivitySuggest
import java.util.*

@Composable
fun HomeScreen() {

}

@Composable
fun HomeContent(
    scaffoldState: ScaffoldState,
    showProgress: Boolean,
    randomActivitySuggest: ActivitySuggest,
    activitySuggestList: List<ActivitySuggest>,
    onAddActivitySuggestClick: (type: String) -> Unit,
    onFabButtonClick: () -> Unit
) {

    var openAddActivitySuggestDialog by remember { mutableStateOf(false) }

    if (openAddActivitySuggestDialog) {
        AddActivitySuggestDialog(
            activitySuggest = randomActivitySuggest,
            onDismiss = { openAddActivitySuggestDialog = false },
            onPositiveButtonClick = { type ->
                onAddActivitySuggestClick(type)
            }
        )
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            DefaultAppBar(title = stringResource(id = R.string.appbar_title))
        },
        content = { padding ->
            Column(modifier = Modifier
                .padding(padding)
            ) {

                ActivitySuggestList(
                    activitySuggestList = activitySuggestList
                )
            }

            if (showProgress) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    CircularProgressIndicator()
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onFabButtonClick()
                    //openAddActivitySuggestDialog = true
                },
                content = {
                    Icon(
                        Icons.Rounded.Add,
                        contentDescription = null
                    )
                }
            )
        }
    )
}

@Composable
fun ActivitySuggestItem(activitySuggest: ActivitySuggest) {
    Column(
        modifier = Modifier.padding(MarginPaddingSizeMedium)
    ) {
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
    }
}

@Composable
fun ActivitySuggestList(activitySuggestList: List<ActivitySuggest>) {
    LazyColumn {
        items(activitySuggestList) { activitySuggest ->
            ActivitySuggestItem(activitySuggest = activitySuggest)
            Divider(color = Color.Black, thickness = 1.dp)
        }
    }
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddActivitySuggestDialog(
    activitySuggest: ActivitySuggest,
    onDismiss: () -> Unit,
    onPositiveButtonClick: (type: String) -> Unit
) {

    val activityTypeList = ActivitySuggestType.values()

    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(activityTypeList[0]) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(
                onClick = {
                    onPositiveButtonClick(selectedOptionText.type)
                    onDismiss()
                },
                content = {
                    Text(
                        text = stringResource(
                            id = R.string.add_activity_suggest_positive_button_dialog
                        )
                    )
                }
            )
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(
                    text = stringResource(id = R.string.add_activity_suggest_negative_button_dialog)
                )
            }
        },
        title = { Text(text = stringResource(id = R.string.add_activity_suggest_title_dialog)) },
        text = {
            Column {
                Column(
                    modifier = Modifier
                        .background(Pink40)
                        .padding(MarginPaddingSizeMedium)
                ) {
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
                }

                ExposedDropdownMenuBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = MarginPaddingSizeMedium),
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    }
                ) {
                    TextField(
                        readOnly = true,
                        value = selectedOptionText.type,
                        onValueChange = { },
                        label = { Text(stringResource(id = R.string.add_activity_suggest_type_dialog)) },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = expanded
                            )
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        }
                    ) {
                        activityTypeList.forEach { selectionOption ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedOptionText = selectionOption
                                    expanded = false
                                }
                            ) {
                                Text(text = selectionOption.type)
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
@Preview
fun ActivitySuggestItemPreview() {
    ActivitySuggestItem(activitySuggest = fakeActivitySuggest)
}

@Composable
@Preview
fun ActivitySuggestListPreview() {
    ActivitySuggestList(
        activitySuggestList = fakeActivitySuggestList
    )
}

@Composable
@Preview
fun HomeContentPreview() {
    HomeContent(
        scaffoldState = rememberScaffoldState(),
        showProgress = true,
        activitySuggestList = fakeActivitySuggestList,
        randomActivitySuggest = fakeActivitySuggest,
        onAddActivitySuggestClick = {},
        onFabButtonClick = {}
    )
}

@Composable
@Preview
fun AddActivitySuggestDialogPreview() {
    AddActivitySuggestDialog(
        activitySuggest = fakeActivitySuggest,
        onDismiss = {},
        onPositiveButtonClick = {}
    )
}

private val fakeActivitySuggest = ActivitySuggest(
    accessibility = 0.0,
    activity = "Organize a bookshelf",
    key = "6098037",
    link = "http://",
    participants = 0,
    price = 0.0,
    type = "busywork",
    status = ActivityStatus.COMPLETED,
    timeSpent = Date(),
    createdAt = Date()
)

private val fakeActivitySuggestList = listOf(
    fakeActivitySuggest,
    fakeActivitySuggest,
    fakeActivitySuggest,
    fakeActivitySuggest,
    fakeActivitySuggest
)