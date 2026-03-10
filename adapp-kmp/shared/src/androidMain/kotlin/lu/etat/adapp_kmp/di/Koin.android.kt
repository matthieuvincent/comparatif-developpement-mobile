package lu.etat.adapp_kmp.di

import lu.etat.adapp_kmp.managers.AccelerometerManager
import lu.etat.adapp_kmp.managers.BatteryManager
import lu.etat.adapp_kmp.managers.DeviceManager
import lu.etat.adapp_kmp.managers.GeolocationManager
import org.koin.dsl.module

actual val platformModule = module {
    single { AccelerometerManager(get()) }
    single { DeviceManager() }
    single { GeolocationManager(get()) }
    single { BatteryManager(get()) }
}