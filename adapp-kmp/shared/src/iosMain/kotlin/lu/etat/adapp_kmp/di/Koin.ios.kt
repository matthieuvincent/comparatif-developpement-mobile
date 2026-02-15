package lu.etat.adapp_kmp.di

import lu.etat.adapp_kmp.managers.AccelerometerManager
import lu.etat.adapp_kmp.managers.BatteryManager
import lu.etat.adapp_kmp.managers.DeviceManager
import lu.etat.adapp_kmp.managers.GeolocationManager
import lu.etat.adapp_kmp.viewmodels.SharedDeviceViewModel
import lu.etat.adapp_kmp.viewmodels.SharedPerformanceViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin
import org.koin.dsl.module

actual val platformModule = module {
    single { AccelerometerManager() }
    single { DeviceManager() }
    single { GeolocationManager() }
    single { BatteryManager() }
}

object KoinHelper : KoinComponent {
    fun getDeviceViewModel(): SharedDeviceViewModel = get()
    fun getPerformanceViewModel(): SharedPerformanceViewModel = get()
}

fun initKoin() {
    startKoin {
        modules(commonModule, platformModule)
    }
}