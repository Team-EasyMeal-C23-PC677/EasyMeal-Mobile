package com.doanda.easymeal.ui.camera

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.doanda.easymeal.R
import com.doanda.easymeal.databinding.ActivityCameraBinding
import com.doanda.easymeal.ui.ViewModelFactory
import com.doanda.easymeal.ui.detection.result.DetectionResultActivity
import com.doanda.easymeal.utils.createFile
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraActivity : AppCompatActivity() {

    private val binding by lazy { ActivityCameraBinding.inflate(layoutInflater) }

    private var imageCapture: ImageCapture? = null
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    private lateinit var cameraExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        cameraExecutor = Executors.newSingleThreadExecutor()
        setupView()
    }

    private fun setupView() {
        binding.btnCapture.setOnClickListener {
            Toast.makeText(this, getString(R.string.detecting), Toast.LENGTH_SHORT).show()
            takePhoto()
        }
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val photoFile = createFile(application)

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    goToResult(photoFile)
                }

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(
                        this@CameraActivity,
                        "Gagal mengambil gambar.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )
    }

    private fun goToResult(photoFile: File) {
        val intent = Intent(this, DetectionResultActivity::class.java)
        intent.putExtra(DetectionResultActivity.EXTRA_PICTURE, photoFile)
        intent.putExtra(
            DetectionResultActivity.EXTRA_IS_BACK_CAMERA,
            cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA
        )
        startActivity(intent)
        finish()
    }

    override fun onResume() {
        super.onResume()
        hideSystemUI()
        startCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    private fun startCamera() {
        val cameraProvideFuture = ProcessCameraProvider.getInstance(this)

        cameraProvideFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProvideFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )
            } catch (e: Exception) {
                Toast.makeText(this, getString(R.string.open_camera_failed), Toast.LENGTH_SHORT).show()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun hideSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            @Suppress("DEPRECATION")
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
}