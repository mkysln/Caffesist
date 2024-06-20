package com.aslansoft.caffesist.views

import android.util.Log
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.camera.core.CameraControl
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aslansoft.caffesist.classes.QRCodeViewModel
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit



@Composable
fun CameraPreview(qrCodeViewModel: QRCodeViewModel = viewModel(),onCameraControl: (CameraControl) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val qrResult = remember {
        mutableStateOf("")
    }
    AndroidView(
        modifier = Modifier
            .fillMaxSize(),
        factory = { AndroidViewContext ->
            PreviewView(AndroidViewContext).apply {
                this.scaleType = PreviewView.ScaleType.FILL_CENTER
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                // Preview is incorrectly scaled in Compose on some devices without this
                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            }
        },
        update = { previewView ->
            val cameraSelector: CameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()
            val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
            val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> =
                ProcessCameraProvider.getInstance(context)

            cameraProviderFuture.addListener({
                val preview: Preview = Preview.Builder()
                    .build()
                    .also {
                        // Attach the viewfinder's surface provider to preview use case
                        it.setSurfaceProvider(previewView.surfaceProvider)

                    }
                val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
                val barcodeAnalyser = BarcodeAnalyser { barcodes ->
                    barcodes.forEach { barcode ->
                        barcode.rawValue?.let { barcodeValue ->
                            Log.e("Camera","Barcode value detected: ${barcodeValue}.")
                            // Other handling code
                            qrCodeViewModel.setQRResult(barcodeValue)
                        }
                    }
                }
                val imageAnalysis: ImageAnalysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .also {
                        it.setAnalyzer(cameraExecutor, barcodeAnalyser)
                    }

                try {
                    cameraProvider.unbindAll()
                    val camera = cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        imageAnalysis
                    )
                    onCameraControl(camera.cameraControl)
                } catch (exception: Exception) {
                    Log.e("Camera","Use case binding failed with exception : $exception")
                }
            }, ContextCompat.getMainExecutor(context))
        },
    )
}

class BarcodeAnalyser(
    private val onBarcodesDetected: (barcodes: List<Barcode>) -> Unit,
) : ImageAnalysis.Analyzer {
    private var lastAnalyzedTimestamp = 0L

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(
        imageProxy: ImageProxy,
    ) {
        Log.d("Barcode Scanner","Inside analyze")
        val currentTimestamp = System.currentTimeMillis()
        if (currentTimestamp - lastAnalyzedTimestamp >= TimeUnit.SECONDS.toMillis(1)) {

            imageProxy.image?.let { imageToAnalyze ->
                val options = BarcodeScannerOptions.Builder()
                    .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
                    .build()
                val barcodeScanner = BarcodeScanning.getClient(options)
                val imageToProcess =
                    InputImage.fromMediaImage(imageToAnalyze, imageProxy.imageInfo.rotationDegrees)

                barcodeScanner.process(imageToProcess)
                    .addOnSuccessListener { barcodes ->
                        if (barcodes.isNotEmpty()) {
                            Log.d("Barcode Scanner","Scanned: $barcodes")
                            onBarcodesDetected(barcodes)
                            imageProxy.close()
                        } else {
                            Log.e("Barcode Scanner","No barcode scanned")
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.e("Barcode Scanner","BarcodeAnalyser: Something went wrong with exception: $exception")

                    }
                    .addOnCompleteListener{
                        imageProxy.close()
                    }
            }
            lastAnalyzedTimestamp = currentTimestamp
        }
        else{
            imageProxy.close()

        }
    }
}