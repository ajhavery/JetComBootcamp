package com.ajhavery.jetnoteapp.data

import androidx.room.*
import com.ajhavery.jetnoteapp.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDatabaseDao {
    // Create CRUD queries

    @Query("SELECT * from notes_tbl")
    fun getNotes(): Flow<List<Note>>
    // getNotes() will execute the Select * query

    @Query("SELECT * from notes_tbl where id =:id")
    suspend fun getNotesById(id: String): Note

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(note: Note)

    @Query("DELETE from notes_tbl")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteNote(note: Note)
}
