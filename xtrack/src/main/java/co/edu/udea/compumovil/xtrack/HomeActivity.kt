package co.edu.udea.compumovil.xtrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import co.edu.udea.compumovil.xtrack.adapter.ExperienceAdapter
import co.edu.udea.compumovil.xtrack.data.DataSource
import co.edu.udea.compumovil.xtrack.viewmodel.ExperienceViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : AppCompatActivity() {

    private lateinit var _experienceAdapter: ExperienceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val experienceDataset = ExperienceViewModel.getAllExperiences(this.applicationContext)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        _experienceAdapter = ExperienceAdapter(experienceDataset)
        recyclerView.adapter = _experienceAdapter



        /*  Pasar a la activity de Experince para crear nueva
         */
        val botonAddExperience = findViewById<FloatingActionButton>(R.id.botonAddExperience)

        botonAddExperience.setOnClickListener{

            val intentAddExperience = Intent(this, ExperienceActivity::class.java)
            startActivity(intentAddExperience)

        }

        val textBuscar = findViewById<EditText>(R.id.search_edittext)

        textBuscar.setOnKeyListener(View.OnKeyListener{ _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                _experienceAdapter.filter.filter(textBuscar.text)
                return@OnKeyListener true
            }
            false

        })

    }
}