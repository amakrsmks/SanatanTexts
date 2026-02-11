package com.amakrsmks.sanatangranth.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "verses",
    indices = [
        Index(
            value = ["bookName", "majorDivision", "minorDivision"],
            name = "idx_book_navigation"
        )
    ]
)
data class Verse(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val bookName: String,
    val majorDivision: String?,
    val minorDivision: String?,
    val verseIdentifier: String?,
    val originalText: String,
    val commentariesJson: String?,
    val translationsJson: String?,
    val metadata: String?
)