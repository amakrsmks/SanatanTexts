package com.amakrsmks.sanatantexts.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.amakrsmks.sanatantexts.data.entity.Verse

@Dao
interface VerseDao {
    @Query("SELECT * FROM verses WHERE bookName = :book AND majorDivision = :division")
    suspend fun getVersesByDivision(book: String, division: String): List<Verse>

    @Query("SELECT * FROM verses WHERE originalText LIKE '%' || :query || '%'")
    suspend fun searchVerses(query: String): List<Verse>
}