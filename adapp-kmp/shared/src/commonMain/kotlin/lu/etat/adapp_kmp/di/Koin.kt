package lu.etat.adapp_kmp.di

import lu.etat.adapp_kmp.viewmodels.SharedDeviceViewModel
import lu.etat.adapp_kmp.viewmodels.SharedPerformanceViewModel
import lu.etat.adapp_kmp.viewmodels.SharedQRCodeViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val commonModule = module {
    factory { SharedDeviceViewModel(get(), get(), get(), get()) }
    factory { SharedQRCodeViewModel() }
    factory { SharedPerformanceViewModel() }
}

expect val platformModule: Module