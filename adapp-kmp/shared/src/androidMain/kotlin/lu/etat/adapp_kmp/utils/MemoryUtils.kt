package lu.etat.adapp_kmp.utils

actual object MemoryUtils {

    actual fun getUsedMemoryMb(): Double {
        val runtime = Runtime.getRuntime()
        val usedBytes = runtime.totalMemory() - runtime.freeMemory()

        return usedBytes / (1024.0 * 1024.0)
    }
}