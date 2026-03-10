package lu.etat.adapp_kmp.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import lu.etat.adapp_kmp.scanners.QrCodeDelegate
import platform.AVFoundation.*
import platform.UIKit.UIColor
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
            container.backgroundColor = UIColor.clearColor

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
                        metadataOutput.setMetadataObjectsDelegate(delegate, dispatch_get_main_queue())
                        metadataOutput.metadataObjectTypes = listOf(AVMetadataObjectTypeQRCode)
                    }

                    dispatch_async(dispatch_get_main_queue()) {
                        val previewLayer = AVCaptureVideoPreviewLayer.layerWithSession(captureSession)
                        previewLayer.videoGravity = AVLayerVideoGravityResizeAspectFill
                        previewLayer.frame = container.bounds
                        container.layer.addSublayer(previewLayer)

                        dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT.toLong(), 0UL)) {
                            captureSession.startRunning()
                        }
                    }
                }
            }
            container
        },
        update = { view ->
            val previewLayer = view.layer.sublayers?.filterIsInstance<AVCaptureVideoPreviewLayer>()?.firstOrNull()
            view.bounds.useContents {
                if (size.width > 0 && size.height > 0) {
                    previewLayer?.frame = view.bounds
                }
            }
        },
        onRelease = {
            dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT.toLong(), 0UL)) {
                captureSession.stopRunning()
            }
        }
    )
}