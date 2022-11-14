package hu.bme.aut.android.mybookshelves.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import hu.bme.aut.android.mybookshelves.model.api.Resource
import java.io.Serializable

@Entity
data class Book (
    @PrimaryKey val bookId: String,
    val title: String?,
    val authors: String?,
    val publishedDate: String?,
    val thumbnail: String?,
    val previewLink: String?,
) : Serializable {
    companion object {
        fun bookFromResource(res: Resource): Book {
            return Book(
                bookId = res.id,
                title = res.volumeInfo?.title,
                authors = res.volumeInfo?.authors?.joinToString(", "),
                publishedDate = res.volumeInfo?.publishedDate,
                thumbnail = res.volumeInfo?.imageLinks?.thumbnail ?: res.volumeInfo?.imageLinks?.smallThumbnail,
                previewLink = res.volumeInfo?.previewLink
            )
        }
    }

}