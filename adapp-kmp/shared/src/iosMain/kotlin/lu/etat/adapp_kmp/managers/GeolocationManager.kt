package lu.etat.adapp_kmp.managers

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import lu.etat.adapp_kmp.models.GeolocationData
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLLocationAccuracyBest
import platform.Foundation.NSError
import platform.darwin.NSObject
import kotlin.random.Random

actual class GeolocationManager: ListeningManager<GeolocationData> {

    private val locationManager = CLLocationManager()

    @OptIn(ExperimentalForeignApi::class)
    actual override fun startListening(): Flow<GeolocationData> = callbackFlow {
        if (!locationManager.locationServicesEnabled()) {

            val job = launch {
                while (isActive) {
                    send(GeolocationData(
                        latitude = 49.6223 + (Random.nextDouble() * 0.001),
                        longitude = 6.1820 + (Random.nextDouble() * 0.001),
                        altitude = Double.MAX_VALUE
                    ))
                    delay(1000)
                }
            }
            awaitClose { job.cancel() }
        } else {
            val delegate = object : NSObject(), CLLocationManagerDelegateProtocol {
                override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
                    val locations = didUpdateLocations as List<CLLocation>
                    locations.lastOrNull()?.let { location ->
                        location.coordinate.useContents {
                            trySend(
                                GeolocationData(
                                    latitude = latitude,
                                    longitude = longitude,
                                    altitude = location.altitude
                                )
                            )
                        }
                    }
                }

                override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {

                }
            }

            locationManager.delegate = delegate
            locationManager.desiredAccuracy = kCLLocationAccuracyBest
            locationManager.requestWhenInUseAuthorization()
            locationManager.startUpdatingLocation()

            awaitClose {
                locationManager.stopUpdatingLocation()
                locationManager.delegate = null
            }
        }
    }

    actual override fun stopListening() {
        locationManager.stopUpdatingLocation()
    }
}