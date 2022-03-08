package co.edu.udea.compumovil.xtrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ExperienceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_experience)

        /*  Pasar a la activity de Mapa para agregar ubicacion
         */

        val mapaImageButton = findViewById<ImageButton>(R.id.mapaImageButton)
        mapaImageButton.setOnClickListener{

            val intentAddLocation = Intent(this, MapActivity::class.java)
            startActivity(intentAddLocation)

        }

        /*  Pasar a la activity de busqueda de fotos para agregar
         */

        val camFloatingActionButton = findViewById<FloatingActionButton>(R.id.camFloatingActionButton)
        camFloatingActionButton.setOnClickListener {

            val intentAddPhoto = Intent(this, TakeSelectPhotoActivity::class.java)
            startActivity(intentAddPhoto)
        }


        /*  Guardar experiencia y retorno a Home
         */

        val saveFloatingActionButton = findViewById<FloatingActionButton>(R.id.saveFloatingActionButton)
        saveFloatingActionButton.setOnClickListener {

            val intentSaveExperience = Intent(this, HomeActivity::class.java)
            startActivity(intentSaveExperience)
        }


    }
}