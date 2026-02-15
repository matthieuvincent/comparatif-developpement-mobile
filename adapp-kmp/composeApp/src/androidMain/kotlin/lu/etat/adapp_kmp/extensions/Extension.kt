package lu.etat.adapp_kmp.extensions

import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics

fun Modifier.accessibleText(text: String): Modifier {
    return this.semantics {
        contentDescription = text
    }
}