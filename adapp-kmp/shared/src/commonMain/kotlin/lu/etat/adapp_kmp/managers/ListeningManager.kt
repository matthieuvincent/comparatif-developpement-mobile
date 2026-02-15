package lu.etat.adapp_kmp.managers

import kotlinx.coroutines.flow.Flow

interface ListeningManager<T> {

    fun startListening(): Flow<T>

    fun stopListening()
}