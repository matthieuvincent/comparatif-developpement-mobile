package lu.etat.adapp_kmp.resources

expect class IconResource

enum class TabItem(val title: String) {
    DEVICE(Labels.DEVICE_TITLE.visual),
    QR_CODE(Labels.QR_TITLE.visual),
    PERFORMANCES(Labels.PERFORMANCE_TITLE.visual);
}

expect fun TabItem.icon(): IconResource