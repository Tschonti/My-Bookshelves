package hu.bme.aut.android.mybookshelves.model.db

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import java.io.Serializable

data class ShelfWithBooks(
    @Embedded val shelf: Bookshelf,
    @Relation(
        parentColumn = "shelfId",
        entityColumn = "bookId",
        associateBy = Junction(BookInShelf::class)
    )
    val books: MutableList<Book>
) : Serializable