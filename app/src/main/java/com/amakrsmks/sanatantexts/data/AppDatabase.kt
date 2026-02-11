package com.amakrsmks.sanatantexts.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.amakrsmks.sanatantexts.data.dao.VerseDao
import com.amakrsmks.sanatantexts.data.entity.Verse

@Database(entities = [Verse::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun verseDao(): VerseDao
}