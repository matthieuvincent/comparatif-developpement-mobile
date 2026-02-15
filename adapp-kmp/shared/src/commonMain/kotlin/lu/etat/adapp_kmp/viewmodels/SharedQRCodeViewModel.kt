package lu.etat.adapp_kmp.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedQRCodeViewModel: ViewModel() {
    private val _scanResult = MutableStateFlow<String?>(null)
    val scanResult: StateFlow<String?> = _scanResult.asStateFlow()

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()

    fun onQrCodeDetected(content: String) {
        _scanResult.value = content
        _showDialog.value = true
    }

    fun dismissDialog() {
        _showDialog.value = false
        _scanResult.value = null
    }
}