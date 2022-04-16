package co.edu.udea.compumovil.xtrack.util

import java.text.SimpleDateFormat
import java.util.*

fun dateToString(dateValue: Date): String {
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return dateFormatter.format(dateValue)
}