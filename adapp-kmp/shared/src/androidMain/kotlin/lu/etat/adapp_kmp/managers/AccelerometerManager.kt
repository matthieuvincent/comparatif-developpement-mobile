package lu.etat.adapp_kmp.managers

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import lu.etat.adapp_kmp.models.AccelerometerData

actual class AccelerometerManager(private val context: Context): ListeningManager<AccelerometerData> {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    actual override fun startListening(): Flow<AccelerometerData> = callbackFlow {
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                if (event != null && event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                    val data = AccelerometerData(
                        x = event.values[0],
                        y = event.values[1],
                        z = event.values[2]
                    )
                    trySend(data)
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

            }
        }

        val supported = sensorManager.registerListener(
            listener,
            accelerometer,
            SensorManager.SENSOR_DELAY_UI
        )

        if (!supported) {
            close()
        }

        awaitClose {
            sensorManager.unregisterListener(listener)
        }
    }

    actual override fun stopListening() {

    }
}