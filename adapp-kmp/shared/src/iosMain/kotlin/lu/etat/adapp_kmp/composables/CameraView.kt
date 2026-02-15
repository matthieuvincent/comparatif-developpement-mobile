package lu.etat.adapp_kmp.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import lu.etat.adapp_kmp.scanners.QrCodeDelegate
import platform.AVFoundation.*
import platform.UIKit.UIView
import platform.darwin.DISPATCH_QUEUE_PRIORITY_DEFAULT
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_global_queue
import platform.darwin.dispatch_get_main_queue

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun CameraView(
    modifier: Modifier,
    onScan: (String) -> Unit
) {
    val delegate = remember { QrCodeDelegate(onScan) }
    val captureSession = remember { AVCaptureSession() }

    UIKitView(
        modifier = modifier,
        factory = {
            val container = UIView()

            // Configuration de la capture en arrière-plan
            dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT.toLong(), 0UL)) {
                val device = AVCaptureDevice.defaultDeviceWithMediaType(AVMediaTypeVideo)
                if (device != null) {
                    val input = AVCaptureDeviceInput.deviceInputWithDevice(device, error = null) as? AVCaptureDeviceInput

                    if (input != null && captureSession.canAddInput(input)) {
                        captureSession.addInput(input)
                    }

                    val metadataOutput = AVCaptureMetadataOutput()
                    if (captureSession.canAddOutput(metadataOutput)) {
                        captureSession.addOutput(metadataOutput)
                        // Le délégué de scan peut rester sur le main queue pour mettre à jour l'UI Compose plus facilement
                        metadataOutput.setMetadataObjectsDelegate(delegate, dispatch_get_main_queue())
                        metadataOutput.metadataObjectTypes = listOf(AVMetadataObjectTypeQRCode)
                    }

                    // Une fois configuré, on revient sur le main pour attacher le layer
                    dispatch_async(dispatch_get_main_queue()) {
                        val previewLayer = AVCaptureVideoPreviewLayer.layerWithSession(captureSession)
                        previewLayer.videoGravity = AVLayerVideoGravityResizeAspectFill
                        previewLayer.frame = container.bounds // Important
                        container.layer.addSublayer(previewLayer)

                        // Lancement final en arrière-plan
                        dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT.toLong(), 0UL)) {
                            captureSession.startRunning()
                        }
                    }
                }
            }
            container
        },
        update = { view ->
            // On s'assure que le layer suit la taille de la vue Compose
            val layer = view.layer.sublayers?.firstOrNull { it is AVCaptureVideoPreviewLayer } as? AVCaptureVideoPreviewLayer
            if (layer != null && (view.bounds.useContents { size.width > 0 && size.height > 0 })) {
                layer.frame = view.bounds
            }
        },
        onRelease = {
            // Très important pour libérer la caméra
            dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT.toLong(), 0UL)) {
                captureSession.stopRunning()
            }
        }
    )
}