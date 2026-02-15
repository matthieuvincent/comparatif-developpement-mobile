package lu.etat.adapp_kmp.managers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import lu.etat.adapp_kmp.models.BatteryData
import android.os.BatteryManager as AndroidBatteryManager

actual class BatteryManager(private val context: Context): ListeningManager<BatteryData> {
    actual override fun startListening(): Flow<BatteryData> = callbackFlow {

        val batteryReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                intent?.let {
                    val level = it.getIntExtra(AndroidBatteryManager.EXTRA_LEVEL, -1)
                    val scale = it.getIntExtra(AndroidBatteryManager.EXTRA_SCALE, -1)

                    if (level != -1 && scale != -1) {
                        val batteryPct = (level * 100 / scale.toFloat()).toInt()
                        trySend(BatteryData(level = batteryPct))
                    }
                }
            }
        }

        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val stickyIntent = context.registerReceiver(batteryReceiver, filter)

        stickyIntent?.let {
            val level = it.getIntExtra(AndroidBatteryManager.EXTRA_LEVEL, -1)
            val scale = it.getIntExtra(AndroidBatteryManager.EXTRA_SCALE, -1)
            if (level != -1 && scale != -1) {
                val batteryPct = (level * 100 / scale.toFloat()).toInt()
                trySend(BatteryData(level = batteryPct))
            }
        }

        awaitClose {
            context.unregisterReceiver(batteryReceiver)
        }
    }

    actual override fun stopListening() {

    }
}