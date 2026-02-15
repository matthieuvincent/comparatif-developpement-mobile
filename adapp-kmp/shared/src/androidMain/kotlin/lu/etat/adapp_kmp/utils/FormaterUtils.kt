package lu.etat.adapp_kmp.utils

actual fun formatDouble(value: Double, decimals: Int): String {
    return "%.${decimals}f".format(value)
}

actual fun formatFloat(value: Float, decimals: Int): String {
    return "%.${decimals}f".format(value)
}