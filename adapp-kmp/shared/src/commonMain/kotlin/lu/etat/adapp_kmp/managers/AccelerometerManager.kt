package lu.etat.adapp_kmp.managers

import kotlinx.coroutines.flow.Flow
import lu.etat.adapp_kmp.models.AccelerometerData

expect class AccelerometerManager: ListeningManager<AccelerometerData> {
    override fun startListening(): Flow<AccelerometerData>
    override fun stopListening()
}