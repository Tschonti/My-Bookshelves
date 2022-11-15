package hu.bme.aut.android.mybookshelves.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import hu.bme.aut.android.mybookshelves.model.api.Resource
import java.io.Serializable

@Entity
data class Book (
    @PrimaryKey(autoGenerate = true) var bookId: Long = 0,
    val googleId: String,
    val title: String?,
    val authors: String?,
    val publishedDate: String?,
    val thumbnail: String?,
    val previewLink: String?,
    val description: String,
    val note: String?
) : Serializable {
    companion object {
        fun bookFromResource(res: Resource): Book {
            return Book(
                googleId = res.id,
                title = res.volumeInfo?.title,
                authors = res.volumeInfo?.authors?.joinToString(", "),
                publishedDate = res.volumeInfo?.publishedDate,
                thumbnail = res.volumeInfo?.imageLinks?.thumbnail ?: res.volumeInfo?.imageLinks?.smallThumbnail,
                previewLink = res.volumeInfo?.previewLink,
                description = res.volumeInfo?.description ?: "No description available.",
                note = null
            )
        }
    }

}