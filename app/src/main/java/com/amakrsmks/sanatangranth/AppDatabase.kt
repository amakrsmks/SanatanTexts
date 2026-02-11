package com.amakrsmks.sanatangranth

import androidx.room.Database
import androidx.room.RoomDatabase
import com.amakrsmks.sanatangranth.dao.VerseDao
import com.amakrsmks.sanatangranth.entity.Verse

@Database(entities = [Verse::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun verseDao(): VerseDao
}