package com.doanda.easymeal.ui.detection

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.doanda.easymeal.R
import com.doanda.easymeal.databinding.ActivityDetectionBinding
import com.doanda.easymeal.ui.camera.CameraActivity
import com.doanda.easymeal.ui.detection.result.DetectionResultActivity
import com.doanda.easymeal.utils.fromUriToFile

class DetectionActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDetectionBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupView()
    }

    private fun setupView() {
        binding.cameraXButton.setOnClickListener {
//            Toast.makeText(this, "Camera clcked", Toast.LENGTH_SHORT).show()
            startCameraX()
        }
        binding.galleryButton.setOnClickListener {
//            Toast.makeText(this, "Gallery clcked", Toast.LENGTH_SHORT).show()
            startGallery()
        }
    }

    private fun startCameraX() {
//        Toast.makeText(this@DetectionActivity, getString(R.string.opening_camera), Toast.LENGTH_SHORT).show()
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this@DetectionActivity,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        } else {
            val intent = Intent(this@DetectionActivity, CameraActivity::class.java)
            startActivity(intent)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, getString(R.string.import_gallery))
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val selectedImage: Uri = it.data?.data as Uri
            val myFile = fromUriToFile(selectedImage, this@DetectionActivity)
            val intent = Intent(this@DetectionActivity, DetectionResultActivity::class.java)
            intent.putExtra(DetectionResultActivity.EXTRA_PICTURE, myFile)
            intent.putExtra(DetectionResultActivity.EXTRA_IS_FROM_GALLERY, true)
            startActivity(intent)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this@DetectionActivity,
                    getString(R.string.permission_camera_not_granted),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            this@DetectionActivity,
            it
        ) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}