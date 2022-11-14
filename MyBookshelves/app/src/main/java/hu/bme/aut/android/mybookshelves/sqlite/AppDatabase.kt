package hu.bme.aut.android.mybookshelves.sqlite

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import hu.bme.aut.android.mybookshelves.model.db.Book
import hu.bme.aut.android.mybookshelves.model.db.BookInShelf
import hu.bme.aut.android.mybookshelves.model.db.Bookshelf

@Database(entities = [Book::class, Bookshelf::class, BookInShelf::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun shelfDao(): ShelfDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context.applicationContext).also { instance = it }
            }
        }
        
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "bookshelves-db").fallbackToDestructiveMigration()
                .build()
        }
    }
}