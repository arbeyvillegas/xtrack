package co.edu.udea.compumovil.xtrack

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat



class TakeSelectPhotoActivity : AppCompatActivity() {



    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_select_photo)
        setImageListener()


    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setImageListener() {

        findViewById<ImageView>(R.id.takeSelectPhotoImageView).setOnClickListener {
            handleImageClick()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun handleImageClick() {
        val pick = true
        if (pick) {
            if (!checkCameraPermission()) {
                requestCameraPermission()
            } else {
                pickImage()
            }
        } else {
            if (!checkStoragePermission()) {
                requestStoragePermission()
            } else {
                pickImage()
            }

        }
    }

    private fun pickImage() {


    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestStoragePermission() {
        requestPermissions(
            arrayOf(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), 100
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestCameraPermission() {
        requestPermissions(
            arrayOf(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), 100
        )
    }

    private fun checkStoragePermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun checkCameraPermission(): Boolean {
        val res1 = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
        val res2 = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        return res1 && res2
    }




}