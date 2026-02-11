package com.amakrsmks.sanatantexts.di

import android.content.Context
import androidx.room.Room
import com.amakrsmks.sanatantexts.data.AppDatabase
import com.amakrsmks.sanatantexts.data.dao.VerseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "sanatan_texts.db"
        )
            .createFromAsset("databases/sanatan_texts.db")
            .fallbackToDestructiveMigration(true)
            .build()
    }

    @Provides
    fun provideVerseDao(database: AppDatabase): VerseDao {
        return database.verseDao()
    }
}