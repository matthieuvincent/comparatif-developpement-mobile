package lu.etat.adapp_kmp.utils

import platform.Foundation.NSString
import platform.Foundation.stringWithFormat

actual fun formatDouble(value: Double, decimals: Int): String {
    return NSString.stringWithFormat("%.${decimals}f", value)
}

actual fun formatFloat(value: Float, decimals: Int): String {
    return NSString.stringWithFormat("%.${decimals}f", value)
}