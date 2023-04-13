package br.com.mobileti.xotedio.home.feature.home.extensions

import br.com.mobileti.xotedio.data.remote.ActivityStatus
import br.com.mobileti.xotedio.design.ui.Green500
import br.com.mobileti.xotedio.design.ui.Red900
import br.com.mobileti.xotedio.design.ui.Yellow500

fun ActivityStatus.getColor() =
    when(this) {
        ActivityStatus.RUNNING -> {
            Yellow500
        }
        ActivityStatus.WAIVER -> {
            Red900
        }
        ActivityStatus.COMPLETED -> {
            Green500
        }
    }
