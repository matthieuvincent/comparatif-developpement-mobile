package lu.etat.adapp_kmp.managers

import lu.etat.adapp_kmp.models.DeviceData
import platform.UIKit.UIDevice

actual class DeviceManager() {

    actual fun getDeviceDetails(): DeviceData {
        val device = UIDevice.currentDevice
        return DeviceData(
            name = device.name,
            manufacturer = "Apple",
            model = device.model,
            osVersion = "${device.systemName} ${device.systemVersion}"
        )
    }
}