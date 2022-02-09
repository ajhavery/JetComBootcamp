package com.ajhavery.jetnoteapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ajhavery.jetnoteapp.model.Note
import com.ajhavery.jetnoteapp.util.DateConverter
import com.ajhavery.jetnoteapp.util.UUIDConverter

@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class, UUIDConverter::class)
abstract class NoteDatabase: RoomDatabase() {
    // function to create the DAO (Data Access Object)
    abstract fun noteDao(): NoteDatabaseDao
}