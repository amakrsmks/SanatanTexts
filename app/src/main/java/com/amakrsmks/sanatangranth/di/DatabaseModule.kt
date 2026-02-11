package com.amakrsmks.sanatangranth.di

import android.content.Context
import androidx.room.Room
import com.amakrsmks.sanatangranth.AppDatabase
import com.amakrsmks.sanatangranth.dao.VerseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
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
            "sanatan_granth.db"
        )
            .createFromAsset("databases/sanatan_granth.db")
            .fallbackToDestructiveMigration(true)
            .build()
    }

    @Provides
    fun provideVerseDao(db: AppDatabase): VerseDao = db.verseDao()
}