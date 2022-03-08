package co.edu.udea.compumovil.xtrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import co.edu.udea.compumovil.xtrack.adapter.ExperienceAdapter
import co.edu.udea.compumovil.xtrack.data.DataSource
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val experienceDataset = DataSource().loadExperiences()

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = ExperienceAdapter(this, experienceDataset)



        /*  Pasar a la activity de Experince para crear nueva
         */
        val botonAddExperience = findViewById<FloatingActionButton>(R.id.botonAddExperience)

        botonAddExperience.setOnClickListener{

            val intentAddExperience = Intent(this, ExperienceActivity::class.java)
            startActivity(intentAddExperience)

        }

    }
}