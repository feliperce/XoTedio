package br.com.mobileti.xotedio.home.feature.home.view

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import br.com.mobileti.xotedio.data.remote.ActivityStatus
import br.com.mobileti.xotedio.design.ui.XoTedioTheme
import br.com.mobileti.xotedio.home.feature.mapper.ActivitySuggest
import org.junit.Rule
import org.junit.Test
import java.util.*

internal class HomeScreenTest {

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

    private val fakeActivitySuggest3 = ActivitySuggest(
        accessibility = 0.0,
        activity = "Organize a bookshelf",
        key = "6098037",
        link = "http://",
        participants = 2,
        price = 0.0,
        type = "busywork",
        status = ActivityStatus.WAIVER,
        timeSpent = null,
        createdAt = Date(1681272000000),
        activityId = 0
    )

    private val fakeActivitySuggestList = listOf(
        fakeActivitySuggest,
        fakeActivitySuggest2,
        fakeActivitySuggest3,
        fakeActivitySuggest,
        fakeActivitySuggest2
    )

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldShowActivityList_whenList() {
        composeTestRule.setContent {
            XoTedioTheme {
                ActivitySuggestList(
                    activitySuggestList = fakeActivitySuggestList,
                    onCompleteButtonClick = {},
                    onWaiverButtonClick = {},
                    onRemoveClick = {}
                )
            }
        }

        composeTestRule.onRoot(useUnmergedTree = true).printToLog("ActivitySuggestList")

        composeTestRule.onNodeWithContentDescription("activitySuggestList")
            .assertIsDisplayed()
    }

    @Test
    fun shouldNotShowActivityList_whenList() {
        composeTestRule.setContent {
            XoTedioTheme {
                ActivitySuggestList(
                    activitySuggestList = emptyList(),
                    onCompleteButtonClick = {},
                    onWaiverButtonClick = {},
                    onRemoveClick = {}
                )
            }
        }

        composeTestRule.onRoot(useUnmergedTree = true).printToLog("ActivitySuggestList")

        composeTestRule.onNodeWithContentDescription("activitySuggestList")
            .assertIsNotDisplayed()
    }

    @Test
    fun shouldShowActionButtons_whenActivityRunning() {
        composeTestRule.setContent {
            XoTedioTheme {
                ActivitySuggestItem(
                    activitySuggest = fakeActivitySuggest2,
                    onCompleteButtonClick = {},
                    onWaiverButtonClick = {},
                    onRemoveClick = {}
                )
            }
        }

        composeTestRule.onRoot(useUnmergedTree = true).printToLog("ActivitySuggestList")

        composeTestRule.onNodeWithContentDescription("activityWaiverButton")
            .assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("activityCompleteButton")
            .assertIsDisplayed()
    }

    @Test
    fun shouldRemoveButtonClickable_whenClick() {
        composeTestRule.setContent {
            XoTedioTheme {
                ActivitySuggestItem(
                    activitySuggest = fakeActivitySuggest,
                    onCompleteButtonClick = {},
                    onWaiverButtonClick = {},
                    onRemoveClick = {}
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("removeButton")
            .assertHasClickAction()
    }

    @Test
    fun shouldShowRemoveDialog_whenRemoveClick() {
        composeTestRule.setContent {
            XoTedioTheme {
                ActivitySuggestItem(
                    activitySuggest = fakeActivitySuggest,
                    onCompleteButtonClick = {},
                    onWaiverButtonClick = {},
                    onRemoveClick = {}
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("removeButton")
            .performClick()
        composeTestRule.onNodeWithContentDescription("removeAlertDialog")
            .assertIsDisplayed()
    }

    @Test
    fun shouldShowCompleteBalloon_whenCompleteActivity() {
        composeTestRule.setContent {
            XoTedioTheme {
                ActivitySuggestItem(
                    activitySuggest = fakeActivitySuggest,
                    onCompleteButtonClick = {},
                    onWaiverButtonClick = {},
                    onRemoveClick = {}
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("statusBalloon")
            .assertTextContains("realizada")
    }

    @Test
    fun shouldShowWaiverBalloon_whenWaiverActivity() {
        composeTestRule.setContent {
            XoTedioTheme {
                ActivitySuggestItem(
                    activitySuggest = fakeActivitySuggest3,
                    onCompleteButtonClick = {},
                    onWaiverButtonClick = {},
                    onRemoveClick = {}
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("statusBalloon")
            .assertTextContains("desistência")
    }

    @Test
    fun shouldShowRunningBalloon_whenRunningActivity() {
        composeTestRule.setContent {
            XoTedioTheme {
                ActivitySuggestItem(
                    activitySuggest = fakeActivitySuggest2,
                    onCompleteButtonClick = {},
                    onWaiverButtonClick = {},
                    onRemoveClick = {}
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("statusBalloon")
            .assertTextContains("em andamento")
    }
}