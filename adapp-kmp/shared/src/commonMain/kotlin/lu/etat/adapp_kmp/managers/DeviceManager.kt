package lu.etat.adapp_kmp.managers

import lu.etat.adapp_kmp.models.DeviceData

expect class DeviceManager {
    fun getDeviceDetails(): DeviceData
}