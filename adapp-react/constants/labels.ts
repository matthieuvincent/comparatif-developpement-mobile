export type AccessibleText = {
    visual: string,
    accessibility: string
}

const createAccessibleText = (visual: string, accessibility?: string): AccessibleText => ({
    visual,
    accessibility: accessibility ?? visual,
});

export const Labels = {
    device: {
        title: createAccessibleText('Appareil'),
        deviceInfoSection: createAccessibleText('Informations de l\'appareil'),
        liveMetricsSection: createAccessibleText('Mesures en temps réél'),
        deviceName: (deviceName: string | null): AccessibleText => {
            let dn = deviceName ?? 'inconnu'
            return createAccessibleText(`Nom de l'appareil : ${dn}`)
        },
        manufacturer: (manufacturer: string | null): AccessibleText => {
            let m = manufacturer ?? 'inconnu'
            return createAccessibleText(`Constructeur : ${m}`)
        },
        model: (model: string | null): AccessibleText => {
            let m = model ?? 'inconnu'
            return createAccessibleText(`Modèle : ${m}`)
        },
        osVersion: (osVersion: string | null): AccessibleText => {
            let ov = osVersion ?? 'inconnu'
            return createAccessibleText(`Version de l'OS : ${ov}`)
        },
        accelrometerX: (accelerometerX: number): AccessibleText => {
            return createAccessibleText(`Accéléromètre X : ${accelerometerX.toFixed(3)}`)
        },
        accelrometerY: (accelerometerY: number): AccessibleText => {
            return createAccessibleText(`Accéléromètre Y : ${accelerometerY.toFixed(3)}`)
        },
        accelrometerZ: (accelerometerZ: number): AccessibleText => {
            return createAccessibleText(`Accéléromètre Z : ${accelerometerZ.toFixed(3)}`)
        },
        latitude: (latitude: number): AccessibleText => {
            return createAccessibleText(`Latitude : ${latitude.toFixed(6)}`)
        },
        longitude: (longitude: number): AccessibleText => {
            return createAccessibleText(`Longitude : ${longitude.toFixed(6)}`)
        },
        altitude: (altitude: number | null): AccessibleText => {
            let alt = altitude?.toFixed(2)?? 'N/A'
            return createAccessibleText(`Altitude : ${alt} m`, `Altitude : ${alt} mètres`)
        },
        batteryLevel: (batteryLevel: number): AccessibleText => {
            return createAccessibleText(`Niveau de la batterie : ${batteryLevel} %`, `Niveau de la batterie : ${batteryLevel} pourcent`)
        }
    },
    qrCode: {
        title: createAccessibleText('QR Code'),
        cameraNotGranted: createAccessibleText('L\'accès à la caméra est nécessaire pour scanner le QR Code.'),
        cameraLoading: createAccessibleText('Chargement de la caméra...'),
        alert: {
            title: createAccessibleText('QR Code scanné'),
            content: (value: string): AccessibleText => {
                return createAccessibleText(`Contenu : ${value}`, `Le contenu du QR Code est ${value}`)
            },
            dismiss: createAccessibleText('Ok', 'Fermer le dialogue')
        }
    },
    performance: {
        title: createAccessibleText('Performance'),
        cpuTestSection: createAccessibleText('Test CPU', 'Section concernant les tests CPU'),
        memoryTestSection: createAccessibleText('Test mémoire', 'Section concernant les tests mémoire'),
        uiTestSection: createAccessibleText('Test UI', 'Section concernant les tests UI'),
        cpuTestStatus: (isRunning: boolean): AccessibleText => {
            return isRunning ?
                createAccessibleText('Statut : En cours...', 'Le test CPU est en cours')
                : createAccessibleText('Statut : Prêt', 'Le test CPU est prêt')
        },
        memoryTestStatus: (isRunning: boolean): AccessibleText => {
            return isRunning ?
                createAccessibleText('Statut : En cours...', 'Le test mémoire est en cours')
                : createAccessibleText('Statut : Prêt', 'Le test mémoire est prêt')
        },
        runCpuTest: createAccessibleText('Lancer 10 cribles d\'ératosthène'),
        runMemoryTest: createAccessibleText('Lancer le test mémoire'),
        cpuAverageTime: (cpuAverageTime: number | null): AccessibleText => {
            let cat = cpuAverageTime?.toFixed(2) ?? '0.00'
            return createAccessibleText(`Temps moyen par cycle : ${cat} ms`, `Temps moyen écoulé par cycle : ${cat} millisecondes`)
        },
        growthRate: (growthRate: number | undefined): AccessibleText => {
            let gr = growthRate?.toFixed(4) ?? '0.0000'
            return createAccessibleText(`Taux de croissance : ${gr} MB/cycle`, `Taux de croissance : ${gr} mégaoctets par cycle`)
        },
        volatility: (volatility: number | undefined): AccessibleText => {
            let v = volatility?.toFixed(4) ?? '0.0000'
            return createAccessibleText(`Volatilité (RMSE) : ${v} MB`, `Volatilité (RMSE) : ${v} mégaoctets`)
        },
        intercept: (intercept: number | undefined): AccessibleText => {
            let i = intercept?.toFixed(4) ?? '0.0000'
            return createAccessibleText(`Empreinte structurelle : ${i} MB`, `Empreinte structurelle : ${i} mégaoctets`)
        },
        memoryAverageTime: (memoryAverageTime: number | undefined): AccessibleText => {
            let mat = memoryAverageTime?.toFixed(2) ?? '0.00'
            return createAccessibleText(`Temps moyen par cycle : ${mat} ms`, `Temps moyen écoulé par cycle : ${mat} millisecondes`)
        }
    }
}