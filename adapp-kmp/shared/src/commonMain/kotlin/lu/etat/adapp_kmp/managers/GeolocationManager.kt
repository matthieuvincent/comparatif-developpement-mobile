package lu.etat.adapp_kmp.managers

import kotlinx.coroutines.flow.Flow
import lu.etat.adapp_kmp.models.GeolocationData

expect class GeolocationManager: ListeningManager<GeolocationData> {
    override fun startListening(): Flow<GeolocationData>
    override fun stopListening()
}