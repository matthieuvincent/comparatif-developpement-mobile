package lu.etat.adapp_kmp.controllers

import androidx.compose.ui.window.ComposeUIViewController
import lu.etat.adapp_kmp.composables.ImagesList
import lu.etat.adapp_kmp.composables.QrScannerScreen

fun QrCodeController() = ComposeUIViewController {
    QrScannerScreen()
}

fun PerformanceController() = ComposeUIViewController {
    ImagesList()
}