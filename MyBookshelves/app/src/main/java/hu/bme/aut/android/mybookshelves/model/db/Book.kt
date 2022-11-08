package hu.bme.aut.android.mybookshelves.model.db

import hu.bme.aut.android.mybookshelves.model.api.Resource
import java.io.Serializable

data class Book (
    val id: String?,
    val title: String?,
    val authors: List<String>?,
    val publishedDate: String?,
    val thumbnail: String?,
    val previewLink: String?,
) : Serializable {
    companion object {
        fun bookFromResource(res: Resource): Book {
            return Book(
                id = res.id,
                title = res.volumeInfo?.title,
                authors = res.volumeInfo?.authors,
                publishedDate = res.volumeInfo?.publishedDate,
                thumbnail = res.volumeInfo?.imageLinks?.thumbnail ?: res.volumeInfo?.imageLinks?.smallThumbnail,
                previewLink = res.volumeInfo?.previewLink
            )
        }
    }

}