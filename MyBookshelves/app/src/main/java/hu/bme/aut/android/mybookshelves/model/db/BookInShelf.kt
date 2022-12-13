package hu.bme.aut.android.mybookshelves.model.db

import androidx.room.Entity

@Entity(primaryKeys = ["bookId", "shelfId"])
data class BookInShelf(
    val bookId: Long,
    val shelfId: Long
)
