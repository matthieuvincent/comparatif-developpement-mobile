package lu.etat.adapp_kmp.resources

actual typealias IconResource = String

actual fun TabItem.icon(): IconResource = when(this) {
    TabItem.DEVICE -> "iphone.gen3"
    TabItem.QR_CODE -> "qrcode"
    TabItem.PERFORMANCES -> "gauge.with.dots.needle.33percent"
}