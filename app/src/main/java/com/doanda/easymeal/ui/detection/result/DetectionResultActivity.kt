package com.doanda.easymeal.ui.detection.result

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.compose.ui.input.key.Key.Companion.Back
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import com.doanda.easymeal.MainActivity
import com.doanda.easymeal.R
import com.doanda.easymeal.data.source.model.DetectionEntity
import com.doanda.easymeal.databinding.ActivityDetectionResultBinding
import com.doanda.easymeal.ui.ViewModelFactory
import com.doanda.easymeal.ui.login.LoginActivity
import com.doanda.easymeal.utils.*
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import com.doanda.easymeal.ml.Model
import java.io.File
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder

class DetectionResultActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDetectionResultBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<DetectionResultViewModel>{ ViewModelFactory.getInstance(this)}


    private val appExecutor: AppExecutors by lazy {
        AppExecutors()
    }

    private var detectedIng: String? = null
    private var labels: Array<String>? = null
    private var confidences: FloatArray? = null
    private var file: File? = null
    private var isBack: Boolean = true
    private var compressingDone: MutableLiveData<Boolean> = MutableLiveData(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupData()
    }

    private fun setupData() {
        showLoading(true)
        file = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(EXTRA_PICTURE, File::class.java)
        } else {
            intent.getSerializableExtra(EXTRA_PICTURE) as File
        }
        isBack = intent.getBooleanExtra(EXTRA_IS_BACK_CAMERA, true)



        appExecutor.diskIO.execute {
            file = reduceFileImage(file as File)
            compressingDone.postValue(true)
        }

        compressingDone.observe(this) { isDone ->
            if (isDone) {
                file?.let { loadDetectionResults(it) }
            }
        }
    }

    private fun loadDetectionResults(file: File) {
        var image = BitmapFactory.decodeFile((file).path)
        val dimension = Math.min(image.width, image.height)
        image = ThumbnailUtils.extractThumbnail(image, dimension, dimension)
        image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false)
        showThumbnail(image)
        showLoading(false)
        classifyImage(image)
    }

    private fun showThumbnail(result: Bitmap) {
        var resultShowed = result
        val isFromGallery = intent.getBooleanExtra(EXTRA_IS_FROM_GALLERY, false)
        if (!isFromGallery) {
            resultShowed = rotateBitmap(resultShowed, isBack)
        }
        binding.ivDetectionImage.setImageBitmap(resultShowed)
    }

    private fun classifyImage(image: Bitmap?) {
        try {
            val model = Model.newInstance(applicationContext)

            // Creates inputs for reference.
            val inputFeature0 =
                TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
            val byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
            byteBuffer.order(ByteOrder.nativeOrder())
            val intValues = IntArray(imageSize * imageSize)
            image!!.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)
            var pixel = 0
            for (i in 0 until imageSize) {
                for (j in 0 until imageSize) {
                    val `val` = intValues[pixel++] // RGB
                    byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 255f))
                    byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 255f))
                    byteBuffer.putFloat((`val` and 0xFF) * (1f / 255f))
                }
            }
            inputFeature0.loadBuffer(byteBuffer)

            // Runs model inference and gets result.
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer
            val confidences = outputFeature0.floatArray
            var maxPos = 0
            var maxConfidence = 0f
            for (i in confidences.indices) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i]
                    maxPos = i
                }
            }
            val classes = arrayOf("jahe", "kunyit", "lengkuas")
//            result!!.text = classes[maxPos]

            this.labels = classes
            this.confidences = confidences
            this.detectedIng = classes[maxPos]

//            var s = ""
//            for (i in classes.indices) {
//                s += String.format("%s: %.1f%%\n", classes[i], confidences[i] * 100)
//            }
//            confidence!!.text = s

            // Releases model resources if no longer used.
            handleDetectionResults()
            model.close()
        } catch (e: IOException) {
            // TODO Handle the exception
        }
    }

    private fun handleDetectionResults() {
        val labels = this.labels
        val confidences = this.confidences
        val detectedIng = this.detectedIng

        if (labels != null && confidences != null && detectedIng != null) {
            val listDetection = mutableListOf<DetectionEntity>()

            var maxConfidence = 0.0f
            var maxPos = 0
            for (i in confidences.indices) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i]
                    maxPos = i
                }
//                val data = viewModel.searchIngredientByName(labels[i])
//                data.confidence = confidences[i]
//                listDetection.add(data)
            }

            if (maxConfidence > 0.5f) {
                //                val bestMatch = viewModel.searchIngredientByName(detectedIng)
//                bestMatch.confidence = confidences[maxPos]
//
//                viewModel.getUser().observe(this) { user ->
//                    if (user.isLogin) {
//                        setupView(user.userId, listDetection, bestMatch)
//                    } else {
//                        Toast.makeText(this, "Not logged in!", Toast.LENGTH_SHORT).show()
////                    goToLogin() TODO implement
//                    }
//                }
                viewModel.searchIngredientByName(detectedIng).observe(this) { listResult ->
                    if (listResult.isNotEmpty()) {
                        val result = listResult.first()
                        val data = DetectionEntity(
                            result.ingId,
                            result.categoryName,
                            result.ingName,
                            result.isHave,
                            confidence = maxConfidence
                        )

//                        viewModel.getUser().observe(this) { user ->
//                            if (user.isLogin) {
//                                setupView(user.userId, listDetection, data)
//                            } else {
//                                Toast.makeText(this, "Not logged in!", Toast.LENGTH_SHORT).show()
////                          goToLogin() TODO implement
//                            }
//                        }
                        viewModel.getLoginStatus().observe(this) {isLogin ->
                            if (isLogin) {
                                viewModel.getUser().observe(this) { user ->
                                    setupView(user.userId, listDetection, data)
                                }
                            } else {
                                Toast.makeText(this, "Not logged in!", Toast.LENGTH_SHORT).show()
//                              goToLogin() TODO implement
                            }
                        }

                    } else {
                        handleNotDetected()
                    }
                }
            } else {
                handleNotDetected()
            }


        }
        // TODO implement list of detection
    }

    private fun handleNotDetected() {
        binding.btnAddPantry.text = getString(R.string.return_to_main)
        binding.btnAddPantry.setOnClickListener {
            goToMain()
        }
    }



    private fun setupView(userId: Int, listDetection: MutableList<DetectionEntity>, bestMatch: DetectionEntity) {
        binding.tvMostLikely.text = getString(R.string.most_likely).format(bestMatch.ingName)

        binding.tvDetection.visibility = View.GONE
        binding.rvDetectedIngredients.visibility = View.GONE


        if (bestMatch.isHave == true) {
            binding.tvMostLikely.text = getString(R.string.already_in_pantry).format(bestMatch.ingName)
            binding.btnAddPantry.text = getString(R.string.return_to_main)
            binding.btnAddPantry.setOnClickListener {
                goToMain()
            }
        } else {
            binding.btnAddPantry.setOnClickListener {
                viewModel.addPantryIngredient(userId, bestMatch.ingId).observe(this) { result ->
                    when (result) {
                        is Result.Success -> {
                            showLoading(false)
                            // TODO handle success
                            Toast.makeText(this, getString(R.string.ingredient_added).format(bestMatch.ingName), Toast.LENGTH_SHORT).show()
                            goToMain()
                        }
                        is Result.Loading -> showLoading(true)
                        is Result.Error -> {
                            showLoading(false)
                            val message = getString(R.string.add_ingredient_failed)
                            Log.e(TAG, result.error + message)
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun goToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }


    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


    companion object {
        const val EXTRA_PICTURE = "extra_picture"
        const val EXTRA_IS_FROM_GALLERY = "extra_is_from_gallery"
        const val EXTRA_IS_BACK_CAMERA = "extra_is_back_camera"
        private val TAG = "DetectionResultActivity"
        private val imageSize = 224
//        private val mInputSize = 224
//        private val mModelPath = "model.tflite"
//        private val mLabelPath = "labels.txt"
    }

    //    private fun classifyImage(file: File) {
//        Toast.makeText(this, "Classifying image", Toast.LENGTH_SHORT).show()
//        val bitmap = BitmapFactory.decodeFile(file.path)
//        val classifier = Classifier(assets, mModelPath, mLabelPath, mInputSize)
//
//        binding.ivDetectionImage.setImageBitmap(bitmap)
//
//        val result = classifier.recognizeImage(bitmap)
//        binding.tvDetection.text = result[0].title
//    }
}