package lu.etat.adapp_kmp.resources

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Speed
import androidx.compose.ui.graphics.vector.ImageVector

actual typealias IconResource = ImageVector

actual fun TabItem.icon(): IconResource = when(this) {
    TabItem.DEVICE -> Icons.Filled.PhoneAndroid
    TabItem.QR_CODE -> Icons.Filled.QrCode
    TabItem.PERFORMANCES -> Icons.Filled.Speed
}