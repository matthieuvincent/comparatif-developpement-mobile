package lu.etat.adapp_kmp.models

data class MemoryTestResultData(val intercept: Double, val slope: Double, val rmse: Double, val averageElapsedTimePerCycle: Double)

class MemoryObjectData(val payloadSize: Int) {
    val payload = ByteArray(payloadSize)
}