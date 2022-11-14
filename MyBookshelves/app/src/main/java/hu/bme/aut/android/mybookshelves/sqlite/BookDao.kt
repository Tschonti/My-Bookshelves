package hu.bme.aut.android.mybookshelves.sqlite

import androidx.room.*
import hu.bme.aut.android.mybookshelves.model.db.Book
import hu.bme.aut.android.mybookshelves.model.db.BookWithShelves

@Dao
interface BookDao {

    @Transaction
    @Query("SELECT * FROM book WHERE bookId = :bookId LIMIT 1")
    fun findById(bookId: String): BookWithShelves

    @Insert
    fun insert(book: Book)

    @Delete
    fun delete(book: Book)
}