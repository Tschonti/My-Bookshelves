package hu.bme.aut.android.mybookshelves.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Bookshelf (
    @PrimaryKey(autoGenerate = true) val shelfId: Long = 0,
    val name: String) : Serializable