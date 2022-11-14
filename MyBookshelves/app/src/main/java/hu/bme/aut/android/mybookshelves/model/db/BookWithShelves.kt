package hu.bme.aut.android.mybookshelves.model.db

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import java.io.Serializable

data class BookWithShelves(
    @Embedded val book: Book,
    @Relation(
        parentColumn = "bookId",
        entityColumn = "shelfId",
        associateBy = Junction(BookInShelf::class)
    )
    val shelves: List<Bookshelf>
) : Serializable