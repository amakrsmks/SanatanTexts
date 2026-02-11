package com.amakrsmks.sanatangranth.dao

import androidx.room.Dao
import androidx.room.Query
import com.amakrsmks.sanatangranth.entity.Verse

@Dao
interface VerseDao {
    @Query("SELECT * FROM verses WHERE bookName = :book")
    suspend fun getVersesByBook(book: String): List<Verse>

    @Query("SELECT * FROM verses WHERE originalText LIKE '%' || :query || '%' LIMIT 100")
    suspend fun searchVerses(query: String): List<Verse>
}