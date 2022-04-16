package co.edu.udea.compumovil.xtrack.adapter

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
import co.edu.udea.compumovil.xtrack.model.ExperienceModel
import co.edu.udea.compumovil.xtrack.viewmodel.ExperienceViewModel

class ExperienceAdapter (
        dataSet: ArrayList<ExperienceViewModel>
    ): RecyclerView.Adapter<ExperienceAdapter.ItemViewHolder>(), Filterable
{

    protected var _experiencesList: ArrayList<ExperienceViewModel>
    protected var _experiencesListFiltered: ArrayList<ExperienceViewModel>

    class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
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
        holder.experienceTitletextView.text = item.title.value
        @DrawableRes val imageResourceId: Int = item.images[0]
        holder.experienceImageView.setImageResource(imageResourceId)
        holder.experienceDateTextView.text = item.experienceDate.value
        holder.experienceLocationTextView.text = item.city.value + ", " + item.location.value
    }

    override fun getItemCount() = _experiencesListFiltered.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(filter: CharSequence?): FilterResults {
                val filterString = filter?.toString() ?: ""
                var filteredList = ArrayList<ExperienceViewModel>()
                if (filterString.isEmpty()) {
                    filteredList = _experiencesList
                } else {
                    _experiencesList.filter {
                        (it.city.value!!.contains(filter!!)) or
                                (it.experienceDate.value!!.contains(filter!!, true)) or
                                (it.title.value!!.contains(filter!!, true)) or
                                (it.location.value!!.contains(filter!!, true))
                    }
                        .forEach{filteredList.add(it)}
                }
                return FilterResults().apply { values = filteredList }
            }

            override fun publishResults(filter: CharSequence?, results: FilterResults?) {
                _experiencesListFiltered = if (results?.values == null)
                    ArrayList()
                else
                    results.values as ArrayList<ExperienceViewModel>
                notifyDataSetChanged()
            }
        }
    }
}