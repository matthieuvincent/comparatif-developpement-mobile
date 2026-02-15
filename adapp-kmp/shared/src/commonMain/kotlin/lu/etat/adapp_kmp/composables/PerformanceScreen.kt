package lu.etat.adapp_kmp.composables

import adapp_kmp.shared.generated.resources.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource

@Composable
fun ImagesList() {
    val images = listOf(
        Res.drawable.image1,
        Res.drawable.image2,
        Res.drawable.image3,
        Res.drawable.image4
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(650.dp)
    ) {
        items(count = 10_000) { index ->
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(images[index % images.size]),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Fit
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .alpha(0.8f)
                        .border(1.dp, Color.LightGray, RectangleShape)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(Color.Green, Color.Yellow)
                            ),
                            shape = RectangleShape
                        )
                )
            }
        }
    }
}