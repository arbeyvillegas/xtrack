package co.edu.udea.compumovil.xtrack

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import co.edu.udea.compumovil.xtrack.databinding.ActivityCarouselBinding
import java.io.ByteArrayOutputStream


class CarouselActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCarouselBinding;
    var uri: Uri? = null
    var uris: ArrayList<Uri>? = null
    var current: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarouselBinding.inflate(layoutInflater)



        setContentView(binding.root)


        uris = ArrayList<Uri>()

        initializeUris()
        setupListeners()

    }

    private fun initializeUris() {


         if (intent.extras!!.containsKey("ImagesUri")) {
            val experienceId = intent.getStringArrayExtra("ImagesUri")
            var s1: String = Uri.decode(experienceId!![0])
            uri = (Uri.parse(s1))
             binding.imageViewPhoto.setImageURI(uri)

        }



    }


    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data?.data

                binding.imageViewPhoto.setImageURI(data)
            }
        }

    private fun setupListeners() {
        binding.buttonNextPhoto.setOnClickListener { handleNextClicked() }
        binding.buttonPreviousPhoto.setOnClickListener { handlePreviousClicked() }
    }

    private fun handlePreviousClicked() {
        current--
        setImageView()

    }

    private fun setImageView() {
        current=mod(current , uris?.size!!)
        binding.imageViewPhoto.setImageURI(uris?.get(current))
    }

    private fun handleNextClicked() {
        current++
        setImageView()
    }
    private fun mod(x: Int, y: Int): Int {
        val result = x % y
        return if (result < 0) result + y else result
    }

}