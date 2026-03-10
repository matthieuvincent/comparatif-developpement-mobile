package lu.etat.adapp_kmp.views

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import lu.etat.adapp_kmp.composables.AccText
import lu.etat.adapp_kmp.composables.DataCard
import lu.etat.adapp_kmp.composables.PageTitle
import lu.etat.adapp_kmp.resources.Labels.DEVICE_INFO_SECTION
import lu.etat.adapp_kmp.resources.Labels.DEVICE_TITLE
import lu.etat.adapp_kmp.resources.Labels.LIVE_METRICS_SECTION
import lu.etat.adapp_kmp.resources.Labels.accelerometerX
import lu.etat.adapp_kmp.resources.Labels.accelerometerY
import lu.etat.adapp_kmp.resources.Labels.accelerometerZ
import lu.etat.adapp_kmp.resources.Labels.altitude
import lu.etat.adapp_kmp.resources.Labels.batteryLevel
import lu.etat.adapp_kmp.resources.Labels.deviceName
import lu.etat.adapp_kmp.resources.Labels.latitude
import lu.etat.adapp_kmp.resources.Labels.longitude
import lu.etat.adapp_kmp.resources.Labels.manufacturer
import lu.etat.adapp_kmp.resources.Labels.model
import lu.etat.adapp_kmp.resources.Labels.osVersion
import lu.etat.adapp_kmp.viewmodels.SharedDeviceViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DeviceView() {
    val deviceVM: SharedDeviceViewModel = koinViewModel()

    var hasLocationPermission by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasLocationPermission = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
    }

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        val isGranted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (isGranted) {
            hasLocationPermission = true
        } else {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    val accelerometerData by deviceVM.accelerometerData.collectAsStateWithLifecycle()
    val batteryData by deviceVM.batteryData.collectAsStateWithLifecycle()
    val geolocationData by if (hasLocationPermission) {
        deviceVM.geolocationData.collectAsStateWithLifecycle()
    } else {
        remember { mutableStateOf(null) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(start = 20.dp, top = 40.dp, end = 20.dp, bottom = 40.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        PageTitle(DEVICE_TITLE)

        DataCard(DEVICE_INFO_SECTION) {
            AccText(deviceName(deviceVM.deviceDetails.name))
            AccText(manufacturer(deviceVM.deviceDetails.manufacturer))
            AccText(model(deviceVM.deviceDetails.model))
            AccText(osVersion(deviceVM.deviceDetails.osVersion))
        }

        DataCard(LIVE_METRICS_SECTION) {
            AccText(accelerometerX(accelerometerData.x))
            AccText(accelerometerY(accelerometerData.y))
            AccText(accelerometerZ(accelerometerData.z))
            Spacer(modifier = Modifier.height(10.dp))
            AccText(latitude(geolocationData?.latitude))
            AccText(longitude(geolocationData?.longitude))
            AccText(altitude(geolocationData?.altitude))
            Spacer(modifier = Modifier.height(10.dp))
            AccText(batteryLevel(batteryData.level))
        }
    }
}