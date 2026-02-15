package lu.etat.adapp_kmp.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import lu.etat.adapp_kmp.extensions.accessibleText
import lu.etat.adapp_kmp.resources.AccessibleText

@Composable
fun PageTitle(accessibleText: AccessibleText, modifier: Modifier = Modifier) {
    Text(
        text = accessibleText.visual,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier.accessibleText(accessibleText.accessibility)
    )
}

@Composable
fun AccText(accessibleText: AccessibleText) {
    Text(accessibleText.visual, modifier = Modifier.accessibleText(accessibleText.accessibility))
}

@Composable
fun DataCard(accessibleText: AccessibleText, content: @Composable ColumnScope.() -> Unit) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            content = {
                Text(text = accessibleText.visual,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.accessibleText(accessibleText.accessibility))
                content()
            }
        )
    }
}