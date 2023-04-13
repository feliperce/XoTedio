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
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.mobileti.commons.extensions.getPassedHours
import br.com.mobileti.commons.extensions.timeMillisToDateFormatString
import br.com.mobileti.xotedio.data.ErrorType
import br.com.mobileti.xotedio.data.remote.ActivityStatus
import br.com.mobileti.xotedio.data.remote.ActivitySuggestType
import br.com.mobileti.xotedio.design.components.BalloonText
import br.com.mobileti.xotedio.design.components.DefaultAppBar
import br.com.mobileti.xotedio.design.ui.*
import br.com.mobileti.xotedio.home.R
import br.com.mobileti.xotedio.home.feature.home.extensions.getColor
import br.com.mobileti.xotedio.home.feature.home.state.HomeIntent
import br.com.mobileti.xotedio.home.feature.home.viewmodel.HomeViewModel
import br.com.mobileti.xotedio.home.feature.mapper.ActivitySuggest
import org.koin.androidx.compose.getViewModel
import java.util.*

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = getViewModel()) {

    val homeUiState by homeViewModel.homeState.collectAsState()

    val scaffoldState = rememberScaffoldState()

    val insertSuccessMsg = stringResource(id = R.string.home_activity_insert_sucess_snackbar)

    homeUiState.error?.let { error ->
        if (error != ErrorType.NONE) {
            val errorMsg = stringResource(id = error.errorMsg)

            LaunchedEffect(error) {
                scaffoldState.snackbarHostState.showSnackbar(message = errorMsg)
                homeUiState.error = null
            }
        }
    }

    if (homeUiState.isInserted) {
        LaunchedEffect(Unit) {
            scaffoldState.snackbarHostState.showSnackbar(
                message = insertSuccessMsg
            )
            homeUiState.isInserted = false
        }
    }

    homeViewModel.sendIntent(
        HomeIntent.GetAllRandomActivitySuggest
    )

    HomeContent(
        scaffoldState = scaffoldState,
        showProgress = homeUiState.loading,
        activitySuggestList = homeUiState.activitySuggestList,
        onAddActivitySuggestClick = {
            homeViewModel.sendIntent(
                HomeIntent.InsertRandomActivitySuggest(type = it)
            )
        },
        onCompleteButtonClick = {
            homeViewModel.sendIntent(
                HomeIntent.UpdateActivitySuggest(activitySuggest = it)
            )
        },
        onWaiverButtonClick = {
            homeViewModel.sendIntent(
                HomeIntent.UpdateActivitySuggest(activitySuggest = it)
            )
        }
    )
}

@Composable
fun HomeContent(
    scaffoldState: ScaffoldState,
    showProgress: Boolean,
    activitySuggestList: List<ActivitySuggest>,
    onAddActivitySuggestClick: (type: String) -> Unit,
    onCompleteButtonClick: (activitySuggest: ActivitySuggest) -> Unit,
    onWaiverButtonClick: (activitySuggest: ActivitySuggest) -> Unit
) {

    var openAddActivitySuggestDialog by remember { mutableStateOf(false) }

    if (openAddActivitySuggestDialog) {
        AddActivitySuggestDialog(
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
                    activitySuggestList = activitySuggestList,
                    onCompleteButtonClick = onCompleteButtonClick,
                    onWaiverButtonClick = onWaiverButtonClick
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
                    openAddActivitySuggestDialog = true
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
fun ActivitySuggestItem(
    activitySuggest: ActivitySuggest,
    onCompleteButtonClick: (activitySuggest: ActivitySuggest) -> Unit,
    onWaiverButtonClick: (activitySuggest: ActivitySuggest) -> Unit
) {

    var activitySuggestStatusState by remember {
        mutableStateOf(activitySuggest.status)
    }

    var activitySuggestTimeSpent by remember {
        mutableStateOf(0L)
    }

    activitySuggestTimeSpent = activitySuggest.timeSpent
        ?: activitySuggest.createdAt.getPassedHours()

    val bgColor = when (activitySuggestStatusState) {
        ActivityStatus.COMPLETED -> {
            Green200
        }
        ActivityStatus.WAIVER -> {
            Red200
        }
        else -> {
            Color.White
        }
    }

    Column(
        modifier = Modifier
            .background(bgColor)
            .padding(MarginPaddingSizeMedium)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .padding(end = MarginPaddingSizeMedium)
                    .fillMaxWidth()
                    .weight(1f),
                text = pluralStringResource(
                    id = R.plurals.activity_suggest_activity_title,
                    count = activitySuggest.participants,
                    activitySuggest.activity, activitySuggest.participants
                ),
                fontWeight = FontWeight.Bold
            )

            BalloonText(
                text = activitySuggestStatusState.status,
                backgroundColor = activitySuggestStatusState.getColor()
            )
        }

        Text(
            text = activitySuggest.type,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Light
        )

        Text(
            modifier = Modifier.padding(top = MarginPaddingSizeMedium),
            text = stringResource(id = R.string.activity_suggest_price_title, activitySuggest.price)
        )
        Text(text = activitySuggest.link)

        Text(
            modifier = Modifier.padding(top = MarginPaddingSizeMedium),
            text = stringResource(id = R.string.activity_suggest_time, activitySuggestTimeSpent)
        )

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            if (activitySuggestStatusState == ActivityStatus.RUNNING) {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .padding(MarginPaddingSizeMedium),
                    onClick = {
                        activitySuggestStatusState = ActivityStatus.COMPLETED
                        val suggest = activitySuggest.copy(
                            status = ActivityStatus.COMPLETED,
                            timeSpent = activitySuggestTimeSpent
                        )
                        onCompleteButtonClick(suggest)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Green500),
                    content = {
                        Text(text = stringResource(id = R.string.activity_suggest_complete_button))
                    }
                )

                Button(
                    modifier = Modifier
                        .weight(1f)
                        .padding(MarginPaddingSizeMedium),
                    onClick = {
                        activitySuggestStatusState = ActivityStatus.WAIVER
                        val suggest = activitySuggest.copy(
                            status = ActivityStatus.WAIVER,
                            timeSpent = activitySuggestTimeSpent
                        )
                        onWaiverButtonClick(suggest)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Red500),
                    content = {
                        Text(text = stringResource(id = R.string.activity_suggest_waiver_button))
                    }
                )
            }
        }
    }
}

@Composable
fun ActivitySuggestList(
    activitySuggestList: List<ActivitySuggest>,
    onCompleteButtonClick: (activitySuggest: ActivitySuggest) -> Unit,
    onWaiverButtonClick: (activitySuggest: ActivitySuggest) -> Unit
) {
    LazyColumn {
        items(activitySuggestList) { activitySuggest ->
            ActivitySuggestItem(
                activitySuggest = activitySuggest,
                onCompleteButtonClick = onCompleteButtonClick,
                onWaiverButtonClick = onWaiverButtonClick
            )
            Divider(color = Color.Black, thickness = 1.dp)
        }
    }
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddActivitySuggestDialog(
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
    ActivitySuggestItem(
        activitySuggest = fakeActivitySuggest,
        onCompleteButtonClick = {},
        onWaiverButtonClick = {}
    )
}

@Composable
@Preview
fun ActivitySuggestListPreview() {
    ActivitySuggestList(
        activitySuggestList = fakeActivitySuggestList,
        onCompleteButtonClick = {},
        onWaiverButtonClick = {}
    )
}

@Composable
@Preview
fun HomeContentPreview() {
    HomeContent(
        scaffoldState = rememberScaffoldState(),
        showProgress = true,
        activitySuggestList = fakeActivitySuggestList,
        onAddActivitySuggestClick = {},
        onWaiverButtonClick = {},
        onCompleteButtonClick = {}
    )
}

@Composable
@Preview
fun AddActivitySuggestDialogPreview() {
    AddActivitySuggestDialog(
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
    timeSpent = 0L,
    createdAt = Date(),
    activityId = 0
)

private val fakeActivitySuggest2 = ActivitySuggest(
    accessibility = 0.0,
    activity = "Organize a bookshelf",
    key = "6098037",
    link = "http://",
    participants = 2,
    price = 0.0,
    type = "busywork",
    status = ActivityStatus.RUNNING,
    timeSpent = null,
    createdAt = Date(1681272000000),
    activityId = 0
)

private val fakeActivitySuggestList = listOf(
    fakeActivitySuggest,
    fakeActivitySuggest2,
    fakeActivitySuggest,
    fakeActivitySuggest,
    fakeActivitySuggest
)