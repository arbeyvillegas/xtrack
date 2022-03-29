package co.edu.udea.compumovil.xtrack.model

data class ExperienceModel(
    val title: String,
    val date: String,
    val city: String,
    val location: String,
    val images: Array<Int>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExperienceModel

        if (title != other.title) return false
        if (date != other.date) return false
        if (city != other.city) return false
        if (location != other.location) return false
        if (!images.contentEquals(other.images)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + city.hashCode()
        result = 31 * result + location.hashCode()
        result = 31 * result + images.contentHashCode()
        return result
    }
}
