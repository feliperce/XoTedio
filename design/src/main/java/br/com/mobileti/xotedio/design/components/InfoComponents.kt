package br.com.mobileti.xotedio.design.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import br.com.mobileti.xotedio.design.ui.Green500
import br.com.mobileti.xotedio.design.ui.MarginPaddingSizeSmall
import br.com.mobileti.xotedio.design.ui.TextSizeSmall

@Composable
fun BalloonText(
    modifier: Modifier = Modifier,
    text: String,
    textSize: TextUnit = TextSizeSmall,
    backgroundColor: Color
) {
    Text(
        text = text,
        fontSize = textSize,
        textAlign = TextAlign.Center,
        modifier = modifier
            .graphicsLayer {
                shape = RoundedCornerShape(8.dp)
                clip = true
            }
            .background(color = backgroundColor)
            .padding(
                horizontal = MarginPaddingSizeSmall,
                vertical = MarginPaddingSizeSmall
            )
    )
}

@Composable
@Preview
fun BalloonTextPreview() {
    BalloonText(text = "Teste", backgroundColor = Green500)
}