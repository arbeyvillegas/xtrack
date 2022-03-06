package co.edu.udea.compumovil.xtrack.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import co.edu.udea.compumovil.xtrack.R
import co.edu.udea.compumovil.xtrack.model.Experience

class ExperienceAdapter (
    private val context: Context,
    private val dataSet: List<Experience>
    ): RecyclerView.Adapter<ExperienceAdapter.ItemViewHolder>()
{
    class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        val experienceTitletextView: TextView = view.findViewById(R.id.experience_title)
        val experienceImageView: ImageView = view.findViewById(R.id.experience_image)
        val experienceDateTextView: TextView = view.findViewById(R.id.experience_date)
        val experienceLocationTextView: TextView = view.findViewById(R.id.experience_location)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_experience, parent, false)

        return ItemViewHolder(adapterLayout)
    }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataSet[position]
        holder.experienceTitletextView.text = item.title
        @DrawableRes val imageResourceId: Int = item.images[0]
        holder.experienceImageView.setImageResource(imageResourceId)
        holder.experienceDateTextView.text = item.date
        holder.experienceLocationTextView.text = item.city + ", " + item.location
    }

    override fun getItemCount() = dataSet.size
}