package lu.etat.adapp_kmp.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.stateIn
import lu.etat.adapp_kmp.managers.AccelerometerManager
import lu.etat.adapp_kmp.managers.BatteryManager
import lu.etat.adapp_kmp.managers.DeviceManager
import lu.etat.adapp_kmp.managers.GeolocationManager
import lu.etat.adapp_kmp.models.AccelerometerData
import lu.etat.adapp_kmp.models.BatteryData
import lu.etat.adapp_kmp.models.DeviceData
import lu.etat.adapp_kmp.models.GeolocationData

class SharedDeviceViewModel(
    private val accelerometerManager: AccelerometerManager,
    private val deviceManager: DeviceManager,
    private val geolocationManager: GeolocationManager,
    private val batteryManager: BatteryManager): ViewModel() {

    private val viewModelScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    val deviceDetails: DeviceData = deviceManager.getDeviceDetails()

    val accelerometerData: StateFlow<AccelerometerData> = accelerometerManager
        .startListening()
        .onCompletion {
            accelerometerManager.stopListening()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(0),
            initialValue = AccelerometerData(0f, 0f, 0f)
        )

    val geolocationData: StateFlow<GeolocationData> = geolocationManager
        .startListening()
        .onCompletion {
            geolocationManager.stopListening()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(0),
            initialValue = GeolocationData(0.0, 0.0, 0.0)
        )

    val batteryData: StateFlow<BatteryData> = batteryManager
        .startListening()
        .onCompletion {
            batteryManager.stopListening()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(0),
            initialValue = BatteryData(0)
        )
}