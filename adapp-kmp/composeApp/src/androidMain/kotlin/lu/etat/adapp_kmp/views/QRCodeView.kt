package lu.etat.adapp_kmp.views

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import lu.etat.adapp_kmp.composables.PageTitle
import lu.etat.adapp_kmp.composables.QrScannerScreen
import lu.etat.adapp_kmp.extensions.accessibleText
import lu.etat.adapp_kmp.resources.Labels.CAMERA_PERMISSION_REQUIRED
import lu.etat.adapp_kmp.resources.Labels.QR_TITLE

@Composable
fun QRCodeView() {
    var hasCameraPermission by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasCameraPermission = isGranted
    }

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        val isGranted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        if (isGranted) {
            hasCameraPermission = true
        } else {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp, 40.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PageTitle(QR_TITLE, Modifier.align(Alignment.Start))

        Box(
            modifier = Modifier
                .size(300.dp)
                .graphicsLayer(clip = true)
                .background(Color.Black)
        ) {
            if (hasCameraPermission) {
                QrScannerScreen()
            } else {
                Text(
                    text = CAMERA_PERMISSION_REQUIRED.visual,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center).accessibleText(CAMERA_PERMISSION_REQUIRED.accessibility)
                )
            }
        }
    }
}