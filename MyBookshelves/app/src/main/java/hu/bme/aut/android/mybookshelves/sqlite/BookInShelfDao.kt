package hu.bme.aut.android.mybookshelves.sqlite

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import hu.bme.aut.android.mybookshelves.model.db.BookInShelf

@Dao
interface BookInShelfDao {
    @Insert
    fun insert(bookInShelf: BookInShelf)

    @Insert
    fun insertAll(bookInShelves: List<BookInShelf>)

    @Delete
    fun delete(bookInShelf: BookInShelf)


}