package co.edu.udea.compumovil.xtrack.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import co.edu.udea.compumovil.xtrack.R
import co.edu.udea.compumovil.xtrack.model.Experience

class ExperienceAdapter (
    private val dataSet: ArrayList<Experience>
    ): RecyclerView.Adapter<ExperienceAdapter.ItemViewHolder>(), Filterable
{

    protected lateinit var _experiencesList: ArrayList<Experience>
    protected lateinit var _experiencesListFiltered: ArrayList<Experience>

    class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        val experienceTitletextView: TextView = view.findViewById(R.id.experience_title)
        val experienceImageView: ImageView = view.findViewById(R.id.experience_image)
        val experienceDateTextView: TextView = view.findViewById(R.id.experience_date)
        val experienceLocationTextView: TextView = view.findViewById(R.id.experience_location)
    }

    init {
        _experiencesList = dataSet
        _experiencesListFiltered = dataSet
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_experience, parent, false)

        return ItemViewHolder(adapterLayout)
    }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = _experiencesListFiltered[position]
        holder.experienceTitletextView.text = item.title
        @DrawableRes val imageResourceId: Int = item.images[0]
        holder.experienceImageView.setImageResource(imageResourceId)
        holder.experienceDateTextView.text = item.date
        holder.experienceLocationTextView.text = item.city + ", " + item.location
    }

    override fun getItemCount() = _experiencesListFiltered.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(filter: CharSequence?): FilterResults {
                val filterString = filter?.toString() ?: ""
                if (filterString.isEmpty()) {
                    _experiencesListFiltered = _experiencesList
                } else {
                    var filteredList = ArrayList<Experience>()
                    _experiencesList.filter {
                        (it.city.contains(filter!!)) or
                                (it.date.contains(filter!!)) or
                                (it.title.contains(filter!!)) or
                                (it.location.contains(filter!!))
                    }
                        .forEach{filteredList.add(it)}
                    _experiencesListFiltered = filteredList
                }
                return FilterResults().apply { values = _experiencesListFiltered }
            }

            override fun publishResults(filter: CharSequence?, results: FilterResults?) {
                _experiencesListFiltered = if (results?.values == null)
                    ArrayList()
                else
                    results.values as ArrayList<Experience>
                notifyDataSetChanged()
            }
        }
    }
}