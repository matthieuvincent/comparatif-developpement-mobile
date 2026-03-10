package lu.etat.adapp_kmp.scanners

import platform.AVFoundation.AVCaptureConnection
import platform.AVFoundation.AVCaptureMetadataOutputObjectsDelegateProtocol
import platform.AVFoundation.AVCaptureOutput
import platform.AVFoundation.AVMetadataMachineReadableCodeObject
import platform.darwin.NSObject

class QrCodeDelegate(private val onScan: (String) -> Unit) : NSObject(), AVCaptureMetadataOutputObjectsDelegateProtocol {

    override fun captureOutput(
        output: AVCaptureOutput,
        didOutputMetadataObjects: List<*>,
        fromConnection: AVCaptureConnection
    ) {
        val metadataObject = didOutputMetadataObjects.firstOrNull() as? AVMetadataMachineReadableCodeObject

        metadataObject?.stringValue?.let { qrValue ->
            onScan(qrValue)
        }
    }
}