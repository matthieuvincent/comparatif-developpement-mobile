package lu.etat.adapp_kmp.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import lu.etat.adapp_kmp.resources.AccessibleText
import lu.etat.adapp_kmp.resources.Labels.ALERT_DISMISS
import lu.etat.adapp_kmp.resources.Labels.ALERT_TITLE
import lu.etat.adapp_kmp.resources.Labels.alertContent
import lu.etat.adapp_kmp.viewmodels.SharedQRCodeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun QrScannerScreen() {
    val viewModel: SharedQRCodeViewModel = koinViewModel()

    val scanResult by viewModel.scanResult.collectAsState()
    val showDialog by viewModel.showDialog.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        CameraView(
            modifier = Modifier.fillMaxSize(),
            onScan = { content ->
                viewModel.onQrCodeDetected(content)
            }
        )

        if (showDialog && scanResult != null) {
            val content: AccessibleText = alertContent(scanResult!!)
            AlertDialog(
                onDismissRequest = { viewModel.dismissDialog() },
                title = { Text(ALERT_TITLE.visual, modifier = Modifier.semantics {
                    contentDescription = ALERT_TITLE.accessibility
                }) },
                text = { Text(content.visual, modifier = Modifier.semantics {
                    contentDescription = content.accessibility
                }) },
                confirmButton = {
                    Button(onClick = { viewModel.dismissDialog() }) {
                        Text(ALERT_DISMISS.visual, modifier = Modifier.semantics {
                            contentDescription = ALERT_DISMISS.accessibility
                        })
                    }
                }
            )
        }
    }
}

@Composable
expect fun CameraView(
    modifier: Modifier = Modifier,
    onScan: (String) -> Unit
)