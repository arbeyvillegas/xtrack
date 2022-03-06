package co.edu.udea.compumovil.xtrack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import co.edu.udea.compumovil.xtrack.adapter.ExperienceAdapter
import co.edu.udea.compumovil.xtrack.data.DataSource

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val experienceDataset = DataSource().loadExperiences()

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = ExperienceAdapter(this, experienceDataset)
    }
}