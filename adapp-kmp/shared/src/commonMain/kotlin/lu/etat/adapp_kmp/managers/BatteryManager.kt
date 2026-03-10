package lu.etat.adapp_kmp.managers

import kotlinx.coroutines.flow.Flow
import lu.etat.adapp_kmp.models.BatteryData

expect class BatteryManager: ListeningManager<BatteryData> {
    override fun startListening(): Flow<BatteryData>
    override fun stopListening()
}