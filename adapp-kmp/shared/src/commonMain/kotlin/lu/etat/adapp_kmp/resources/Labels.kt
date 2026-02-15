package lu.etat.adapp_kmp.resources

import lu.etat.adapp_kmp.utils.formatDouble
import lu.etat.adapp_kmp.utils.formatFloat

data class AccessibleText(
    val visual: String,
    val accessibility: String = visual
)

object Labels {
    // TITLE
    val DEVICE_TITLE: AccessibleText = AccessibleText("Appareil")
    val QR_TITLE: AccessibleText = AccessibleText("QR Code")
    val PERFORMANCE_TITLE: AccessibleText = AccessibleText("Performance")

    // DEVICE PAGE
    val DEVICE_INFO_SECTION: AccessibleText = AccessibleText("Informations de l'appareil")
    val LIVE_METRICS_SECTION: AccessibleText = AccessibleText("Mesures en temps réél")
    fun deviceName(deviceName: String): AccessibleText {
        return AccessibleText("Nom de l'appareil : $deviceName")
    }
    fun manufacturer(manufacturer: String): AccessibleText {
        return AccessibleText("Constructeur : $manufacturer")
    }
    fun model(model: String): AccessibleText {
        return AccessibleText("Modèle : $model")
    }
    fun osVersion(osVersion: String): AccessibleText {
        return AccessibleText("Version de l'OS : $osVersion")
    }
    fun accelerometerX(accelerometerX: Float): AccessibleText {
        return AccessibleText("Accéléromètre X : ${formatFloat(accelerometerX, 3)}")
    }
    fun accelerometerY(accelerometerY: Float): AccessibleText {
        return AccessibleText("Accéléromètre Y : ${formatFloat(accelerometerY, 3)}")
    }
    fun accelerometerZ(accelerometerZ: Float): AccessibleText {
        return AccessibleText("Accéléromètre Z : ${formatFloat(accelerometerZ, 3)}")
    }
    fun latitude(latitude: Double?): AccessibleText {
        val value = latitude?.let { formatDouble(it, 6) } ?: "NaN"
        return AccessibleText("Latitude : $value", "Latitude : ${if (latitude == null) "indisponible" else value}")
    }
    fun longitude(longitude: Double?): AccessibleText {
        val value = longitude?.let { formatDouble(it, 6) } ?: "NaN"
        return AccessibleText("Longitude : $value", "Latitude : ${if (longitude == null) "indisponible" else value}")
    }
    fun altitude(altitude: Double?): AccessibleText {
        val value = altitude?.let { formatDouble(it, 2) } ?: "NaN"
        return AccessibleText(
            "Altitude : $value m",
            "Latitude : ${if (altitude == null) "indisponible" else "$value mètres"}")
    }
    fun batteryLevel(batteryLevel: Int): AccessibleText {
        return AccessibleText(
            "Niveau de la batterie : $batteryLevel %",
            "Niveau de la batterie : $batteryLevel pourcent")
    }

    // QR CODE PAGE
    val CAMERA_PERMISSION_REQUIRED: AccessibleText = AccessibleText("Autorisation d'accès à l'appareil photo requise")
    val ALERT_TITLE: AccessibleText = AccessibleText("QR Code scanné")
    val ALERT_DISMISS: AccessibleText = AccessibleText("Ok", "Fermer le dialogue")
    fun alertContent(data: String): AccessibleText {
        return AccessibleText("Contenu : $data", "Le contenu du QR Code est $data")
    }

    // PERFORMANCE PAGE
    val CPU_TEST_SECTION: AccessibleText = AccessibleText("Test CPU", "Section concernant les tests CPU")
    val MEMORY_TEST_SECTION: AccessibleText = AccessibleText("Test mémoire", "Section concernant les tests mémoire")
    val UI_TEST_SECTION: AccessibleText = AccessibleText("Test UI", "Section concernant les tests UI")
    val RUN_CPU_TEST: AccessibleText = AccessibleText("Lancer 10 cribles d'ératosthène")
    val RUN_MEMORY_TEST: AccessibleText = AccessibleText("Lancer le test mémoire")
    fun cpuTestStatus(isRunning: Boolean): AccessibleText {
        return if (isRunning)
            AccessibleText("Statut : En cours...", "Le test CPU est en cours")
        else
            AccessibleText("Statut : Prêt", "Le test CPU est prêt")
    }
    fun memoryTestStatus(isRunning: Boolean): AccessibleText {
        return if (isRunning)
            AccessibleText("Statut : En cours...", "Le test mémoire est en cours")
        else
            AccessibleText("Statut : Prêt", "Le test mémoire est prêt")
    }
    fun cpuAverageTime(cpuAverageTime: Double): AccessibleText {
        val value = formatDouble(cpuAverageTime, 2)
        return AccessibleText("Temps moyen par cycle : $value ms", "Temps moyen écoulé par cycle : $value millisecondes")
    }
    fun growthRate(growthRate: Double): AccessibleText {
        val value = formatDouble(growthRate, 4)
        return AccessibleText("Taux de croissance : $value MB/cycle", "Taux de croissance : $value mégaoctets par cycle")
    }
    fun volatility(volatility: Double): AccessibleText {
        val value = formatDouble(volatility, 4)
        return AccessibleText("Volatilité (RMSE) : $value MB", "Volatilité (RMSE) : $value mégaoctets")
    }
    fun intercept(intercept: Double): AccessibleText {
        val value = formatDouble(intercept, 4)
        return AccessibleText("Empreinte structurelle : $value MB", "Empreinte structurelle : $value mégaoctets")
    }
    fun memoryAverageTime(memoryAverageTime: Double): AccessibleText {
        val value = formatDouble(memoryAverageTime, 2)
        return AccessibleText("Temps moyen par cycle : $value ms", "Temps moyen écoulé par cycle : $value millisecondes")
    }
}
