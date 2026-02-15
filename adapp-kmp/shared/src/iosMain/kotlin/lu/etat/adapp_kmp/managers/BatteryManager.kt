package lu.etat.adapp_kmp.managers

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import lu.etat.adapp_kmp.models.BatteryData
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSOperationQueue
import platform.UIKit.UIDevice

actual class BatteryManager: ListeningManager<BatteryData> {

    private val device = UIDevice.currentDevice

    actual override fun startListening(): Flow<BatteryData> = callbackFlow {
        device.batteryMonitoringEnabled = true

        val observer = NSNotificationCenter.defaultCenter.addObserverForName(
            name = platform.UIKit.UIDeviceBatteryLevelDidChangeNotification,
            `object` = null,
            queue = NSOperationQueue.mainQueue
        ) { _ ->
            val level = device.batteryLevel
            if (level >= 0f) {
                trySend(BatteryData((level * 100).toInt()))
            }
        }

        val initialLevel = device.batteryLevel
        if (initialLevel >= 0f) {
            trySend(BatteryData((initialLevel * 100).toInt()))
        }

        awaitClose {
            NSNotificationCenter.defaultCenter.removeObserver(observer)
            device.batteryMonitoringEnabled = false
        }
    }

    actual override fun stopListening() {
        device.batteryMonitoringEnabled = false
    }
}