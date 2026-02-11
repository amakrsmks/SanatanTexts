package com.amakrsmks.sanatantexts.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "verses")
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