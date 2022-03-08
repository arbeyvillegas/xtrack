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

    }
}