package co.edu.udea.compumovil.xtrack

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import  co.edu.udea.compumovil.xtrack.databinding.ActivityTakeSelectPhotoBinding
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class TakeSelectPhotoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTakeSelectPhotoBinding
    private lateinit var imageURI: Uri


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTakeSelectPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestPermission()
        setupListeners()

    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun setupListeners() {
        binding.buttonTakeFromGalery.setOnClickListener { pickPhotoFromGallery() }
        binding.buttonTakePhoto.setOnClickListener { takePhoto() }
        binding.buttonSavePhoto.setOnClickListener { saveImage(this@TakeSelectPhotoActivity, imageURI) }

    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun takePhoto() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePhoto ->
            takePhoto.resolveActivity(packageManager)?.also {
                startForResult.launch(takePhoto)

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun pickPhotoFromGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startForResult.launch(intent)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun requestPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) -> {
                }
                else -> requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) -> {
                }
                else -> requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data?.data
                if (data == null) {
                    result.data?.extras?.let { bundle ->
                        val bitmap = bundle.get("data") as Bitmap

                        val bytes = ByteArrayOutputStream()
                        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
                        val path: String = MediaStore.Images.Media.insertImage(
                            contentResolver,
                            bitmap,
                            "Title",
                            null
                        )
                        imageURI = Uri.parse(path);
                    }
                } else {
                    imageURI = data;

                }
                binding.imageViewSelectedPhoto.setImageURI(imageURI)
            }
        }

    @RequiresApi(Build.VERSION_CODES.P)
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->

        if (isGranted) {
            pickPhotoFromGallery()
        } else {
            Toast.makeText(
                this,
                "Permission denied",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    private fun saveImage(context: Context, uri: Uri) {
        val id = generateId()
        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)

        try {
            val file = File(context.filesDir, id+"image.jpg");
            val fOut = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut)
            fOut.flush()
            fOut.close()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i(null, "Save file error!")
            return
        }

        var i=0;



    }

    private fun generateId(): String {
        return System.currentTimeMillis().toString();
    }

}