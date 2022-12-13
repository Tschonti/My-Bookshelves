package hu.bme.aut.android.mybookshelves.sqlite

import androidx.room.*
import hu.bme.aut.android.mybookshelves.model.db.Bookshelf
import hu.bme.aut.android.mybookshelves.model.db.ShelfWithBooks

@Dao
interface ShelfDao {
    @Transaction
    @Query("SELECT * FROM bookshelf")
    fun getAll(): List<ShelfWithBooks>

    @Query("SELECT * FROM bookshelf")
    fun getAllWithoutBooks(): List<Bookshelf>

    @Transaction
    @Query("SELECT * FROM bookshelf WHERE shelfId = :shelfId LIMIT 1")
    fun findById(shelfId: Long): ShelfWithBooks

    @Insert
    fun insert(shelf: Bookshelf): Long

    @Delete
    fun delete(shelf: Bookshelf)
}