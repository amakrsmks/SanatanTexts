package com.amaksmks.rawdataprocessor

import kotlinx.serialization.Serializable

@Serializable
data class Verse(
    val bookName: String,
    val majorDivision: String? = null,      // Kaand, Mandala, Parva
    val minorDivision: String? = null,      // Chapter, Sukta, Sarg
    val verseIdentifier: String? = null,    // 1.1, 01001000a, etc.
    val originalText: String,
    val translationsJson: String? = null,   // Store Map as String
    val commentariesJson: String? = null,   // Store Map as String
    val metadata: String? = null            // For "type" like Doha/Chaupai
)