package co.edu.udea.compumovil.xtrack.util

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore


class RealPathUtils {

    @SuppressLint("Range")
    fun getRealPathFromURI(contentURI: Uri, resolver:ContentResolver): String? {

        var cursor: Cursor? = resolver.query(contentURI, null, null, null, null)
        val result: String

        // for API 19 and above

        // for API 19 and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            cursor?.moveToFirst()
            var image_id = cursor?.getString(0)
            image_id = image_id?.substring(image_id.lastIndexOf(":") + 1)
            cursor?.close()
            cursor = resolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,
                MediaStore.Images.Media._ID + " = ? ",
                arrayOf(image_id),
                null
            )
        }

        cursor?.moveToFirst()
        result = cursor?.let { cursor.getString(it.getColumnIndex(MediaStore.Images.Media.DATA)) }!!
        cursor?.close()
        return result
    }

}