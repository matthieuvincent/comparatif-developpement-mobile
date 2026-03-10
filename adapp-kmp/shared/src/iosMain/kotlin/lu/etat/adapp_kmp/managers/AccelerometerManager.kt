package lu.etat.adapp_kmp.managers

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import lu.etat.adapp_kmp.models.AccelerometerData
import platform.CoreMotion.CMMotionManager
import platform.Foundation.NSOperationQueue
import kotlin.random.Random

actual class AccelerometerManager: ListeningManager<AccelerometerData> {

    private val motionManager = CMMotionManager()

    @OptIn(ExperimentalForeignApi::class)
    actual override fun startListening(): Flow<AccelerometerData> = callbackFlow {
        if (!motionManager.isAccelerometerAvailable()) {
            val job = launch {
                while (isActive) {
                    send(AccelerometerData(
                        x = Random.nextFloat() * 2 - 1,
                        y = Random.nextFloat() * 2 - 1,
                        z = Random.nextFloat() * 2 - 1
                    ))
                    delay(1000)
                }
            }
            awaitClose { job.cancel() }
        } else {
            motionManager.accelerometerUpdateInterval = 0.1
            motionManager.startAccelerometerUpdatesToQueue(NSOperationQueue.mainQueue) { data, _ ->
                data?.acceleration?.useContents {
                    trySend(AccelerometerData(
                        x = x.toFloat(),
                        y = y.toFloat(),
                        z = z.toFloat()
                    ))
                }
            }
            awaitClose { motionManager.stopAccelerometerUpdates() }
        }
    }

    actual override fun stopListening() {
        motionManager.stopAccelerometerUpdates()
    }
}