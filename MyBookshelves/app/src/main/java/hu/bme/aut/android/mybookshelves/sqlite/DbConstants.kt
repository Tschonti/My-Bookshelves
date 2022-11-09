package hu.bme.aut.android.mybookshelves.sqlite

import android.database.sqlite.SQLiteDatabase
import android.util.Log

object DbConstants {
    const val DATABASE_NAME = "mybookshelves.db"
    const val DATABASE_VERSION = 1

    object Books {
        const val DATABASE_TABLE = "books"

        enum class Columns {
            ID, TITLE, AUTHORS, PUBLISHED_DATE, THUMBNAIL, PREVIEW_LINK
        }

        private val DATABASE_CREATE = """create table if not exists $DATABASE_TABLE (
            ${Columns.ID.name} integer primary key autoincrement,
            ${Columns.TITLE.name} TEXT not null,
            ${Columns.AUTHORS} text
            ${Columns.PUBLISHED_DATE} text
            ${Columns.THUMBNAIL} text
            ${Columns.PREVIEW_LINK} text
            );"""

        private const val DATABASE_DROP = "drop table if exists $DATABASE_TABLE;"

        fun onCreate(database: SQLiteDatabase) {
            database.execSQL(DATABASE_CREATE)
        }

        fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            Log.w(
                Books::class.java.name,
                "Upgrading from version $oldVersion to $newVersion"
            )
            database.execSQL(DATABASE_DROP)
            onCreate(database)
        }
    }

    object Shelves {
        const val DATABASE_TABLE = "shelves"

        enum class Columns {
            ID, NAME
        }

        private val DATABASE_CREATE = """create table if not exists $DATABASE_TABLE (
            ${Columns.ID.name} integer primary key autoincrement,
            ${Columns.NAME.name} TEXT not null
            );"""

        private const val DATABASE_DROP = "drop table if exists $DATABASE_TABLE;"

        fun onCreate(database: SQLiteDatabase) {
            database.execSQL(DATABASE_CREATE)
        }

        fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            Log.w(
                Shelves::class.java.name,
                "Upgrading from version $oldVersion to $newVersion"
            )
            database.execSQL(DATABASE_DROP)
            onCreate(database)
        }
    }
}