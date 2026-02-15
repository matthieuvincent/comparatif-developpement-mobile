package lu.etat.adapp_kmp.managers

import android.os.Build
import lu.etat.adapp_kmp.models.DeviceData

actual class DeviceManager() {

    actual fun getDeviceDetails(): DeviceData {
        return DeviceData(Build.DEVICE, Build.MANUFACTURER, Build.MODEL, Build.VERSION.RELEASE)
    }
}