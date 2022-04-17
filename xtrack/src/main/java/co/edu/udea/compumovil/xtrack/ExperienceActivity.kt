package co.edu.udea.compumovil.xtrack

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import co.edu.udea.compumovil.xtrack.databinding.ActivityExperienceBinding
import co.edu.udea.compumovil.xtrack.viewmodel.ExperienceViewModel
import co.edu.udea.compumovil.xtrack.viewmodel.ExperienceViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ExperienceActivity : AppCompatActivity() {

    private lateinit var viewModel: ExperienceViewModel
    private lateinit var viewModelFactory: ExperienceViewModelFactory
    private lateinit var binding: ActivityExperienceBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("ExperienceActivity", "Called ViewModelProvider.get")
        //binding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_experience, null, false)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_experience)

        viewModelFactory = ExperienceViewModelFactory(this.applicationContext)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ExperienceViewModel::class.java)

        if (intent.extras!!.containsKey("ExperienceId")) {
            val experienceId = intent.getLongExtra("ExperienceId", -1L)
            if(experienceId != -1L) {
                viewModel.loadExperience(experienceId)
                viewModel.updating = true
            } else {
                viewModel.updating = false
            }
        }

        binding.viewModel = viewModel;
        val view = binding.root
        setContentView(view)

        /*  Pasar a la activity de Mapa para agregar ubicacion
         */

        val mapaImageButton = findViewById<ImageButton>(R.id.mapaImageButton)
        mapaImageButton.setOnClickListener {

            val intentAddLocation = Intent(this, MapActivity::class.java)
            startActivity(intentAddLocation)

        }

        /*  Pasar a la activity de busqueda de fotos para agregar
         */

        val camFloatingActionButton =
            findViewById<FloatingActionButton>(R.id.camFloatingActionButton)
        camFloatingActionButton.setOnClickListener {

            val intentAddPhoto = Intent(this, TakeSelectPhotoActivity::class.java)
            startActivity(intentAddPhoto)
        }


        /*  Guardar experiencia y retorno a Home
         */

        val saveFloatingActionButton =
            findViewById<FloatingActionButton>(R.id.saveFloatingActionButton)
        saveFloatingActionButton.setOnClickListener {
            viewModel.saveExperience()
            val intentSaveExperience = Intent(this, HomeActivity::class.java)
            startActivity(intentSaveExperience)
        }


        /*  Guardar la fecha de un dataPicker a la caja de texto
        */
        val dateEditTextDate = findViewById<EditText>(R.id.dateEditTextDate)
        dateEditTextDate.setOnClickListener {
            showDatePickerDialog()
        }

    }


    /*  Mostrar dataPicker en un dialogo
    */
    private fun showDatePickerDialog() {
        val newFragment =
            DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
                // +1 because January is zero
                val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
                val dateEditTextDate = findViewById<EditText>(R.id.dateEditTextDate)
                dateEditTextDate.setText(selectedDate)
            })

        newFragment.show(supportFragmentManager, "datePicker")
    }
}