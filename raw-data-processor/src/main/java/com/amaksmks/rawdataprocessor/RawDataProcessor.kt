package com.amaksmks.rawdataprocessor

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import java.io.File
import java.sql.DriverManager
import kotlinx.serialization.json.decodeFromStream
import java.io.FileInputStream

/**
 * Ramcharitmanas
 * [
 *     {
 *         "type": "श्लोक",
 *         "content": "वर्णानामर्थसंघानां रसानां छन्दसामपि।\n\nमङ्गलानां च कर्त्तारौ वन्दे वाणीविनायकौ।।1।।",
 *         "kaand": "बालकाण्ड"
 *     },
 *     ...
 * ]
 */
@Serializable data class RamcharitmanasDTO(val type: String, val content: String, val kaand: String)

/**
 * ValmikiRamayana
 * [
 *   {
 *     "kaanda": "balakanda",
 *     "sarg": 1,
 *     "shloka": 1,
 *     "text": "तपस्स्वाध्याय निरतं तपस्वी वाग्विदां वरम् ।नारदं परिपप्रच्छ वाल्मीकिर्मुनिपुंगवम् ॥१-१-१॥"
 *   },
 *   ...
 * ]
 */
@Serializable data class ValmikiRamayanaDTO(val kaanda: String, val sarg: Int, val shloka: Int, val text: String)

/**
 * Rigveda
 * [
 *     {
 *         "veda": "rigveda",
 *         "mandala": 1,
 *         "sukta": 1,
 *         "text": "९ मधुच्छन्दा वैश्वामित्रः । अग्निः। गायत्री।"
 *     },
 *     ...
 * ]
 */
@Serializable data class RigvedaDTO(val veda: String, val mandala: Int, val sukta: Int, val text: String)

/**
 * SrimadBhagvadGita
 * {
 *     "BhagavadGitaChapter": [
 *         {
 *             "chapter": 1,
 *             "verse": 1,
 *             "text": "धृतराष्ट्र उवाच\n\nधर्मक्षेत्रे कुरुक्षेत्रे समवेता युयुत्सवः।\n\nमामकाः पाण्डवाश्चैव किमकुर्वत सञ्जय।।1.1।।",
 *             "commentaries": {
 *                 "Sri Harikrishnadas Goenka": "।।1.1।।Sri Sankaracharya did not comment on this sloka.",
 *                 ...
 *             },
 *             "translations": {
 *                 "sri harikrishnadas goenka": "।।1.1।।Sri Sankaracharya did not comment on this sloka.",
 *                 ...
 *             }
 *         },
 *         ...
 *     ]
 * }
 */
@Serializable
data class SrimadBhagvadGitaWrapper(
    @SerialName("BhagavadGitaChapter")
    val bhagavadGitaChapter: List<SrimadBhagvadGitaVerseDTO>
)

@Serializable data class SrimadBhagvadGitaVerseDTO(
    val chapter: Int, val verse: Int, val text: String,
    val commentaries: Map<String, String>, val translations: Map<String, String>
)


/**
 * Mahabharata
 * {
 *   "18001001c": {
 *     "text": {
 *       "ud": "जनमेजय उवाच\n",
 *       "ur": "janamejaya uvāca\n",
 *       "ascii": "janamejaya uvAca\n"
 *     }
 *   },
 *   ...
 * }
 *
 * Each line of each verse is tagged by a key of the form: 01234567c
 * Where 01 is the name of the book from Ādi (01) to Svargārohaṇa (18) parva.
 * 234 is the chapter number.
 * 567 is the verse number.
 * The letter, usually a or c denotes the first or second half of the śloka, in other words ślokārdha.
 */
@Serializable data class MahabharataValueDTO(val text: MahabharataTextDTO)

@Serializable data class MahabharataTextDTO(val ud: String, val ur: String, val ascii: String)

enum class MahabharataParva(val id: Int, val devnagari: String) {
    ADI(1, "आदी पर्व"),
    SABHA(2, "सभापर्व"),
    VANA(3, "वनपर्व"),
    VIRATA(4, "विराटपर्व"),
    UDYOGA(5, "उद्योगपर्व"),
    BHISHMA(6, "भीष्मपर्व"),
    DRONA(7, "द्रोणपर्व"),
    KARNA(8, "कर्णपर्व"),
    SHALYA(9, "शल्यपर्व"),
    SAUPTIKA(10, "सौप्तिकपर्व"),
    STRI(11, "स्त्रीपर्व"),
    SHANTI(12, "शान्तिपर्व"),
    ANUSHASAN(13, "अनुशासनपर्व"),
    APAROKSHA(14, "अपरोक्षविजयपर्व"),
    ASHVAMEDHA(15, "अश्वमेधपर्व"),
    MUKTI(16, "मुक्तिपर्व"),
    SVARGAROHANA(17, "स्वर्गारोहणिका पर्व"),
    UPSAMHAR(18, "उपसंहार / अंतिम पर्व");

    companion object {
        fun fromId(id: Int): String = entries.find { it.id == id }?.devnagari ?: "अज्ञात पर्व"
    }
}

/**
 * AtharvaVeda
 * [
 *     {
 *         "veda": "atharvaveda",
 *         "samhita": "shaunak",
 *         "kaanda": 1,
 *         "sukta": 1,
 *         "text": "मेधाजननम्।\n१-४ अथर्वा। वाचस्पतिः। अनुष्टुप्,"
 *     },
 *     ...
 * ]
 */
@Serializable
data class AtharvaVedaDTO(val veda: String, val samhita: String, val kaanda: Int, val sukta: Int, val text: String)

/**
 * yajurveda
 * [
 *     {
 *         "veda": "yajurveda",
 *         "samhita": "vajasneyi-kanva-samhita",
 *         "chapter": 2,
 *         "text": ""
 *     },
 *     ...
 * ]
 */
@Serializable
data class YajurvedaKanvaDTO(
    val veda: String,
    val samhita: String,
    val chapter: Int,
    val text: String
)

/**
 * yajurveda
 * [
 *     {
 *         "veda": "yajurveda",
 *         "samhita": "vajasneyi-madhyandina-samhita",
 *         "adhyaya": 1,
 *         "text": ""
 *     },
 *     ...
 * ]
 */
@Serializable
data class YajurvedaMadhyandinaDTO(
    val veda: String,
    val samhita: String,
    val adhyaya: Int,
    val text: String
)


fun main() {
    try {
        RawDataProcessor().run()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


class RawDataProcessor {
    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    // Clone the repo: https://github.com/bhavykhatri/DharmicData.git
    private val inputDir = File("raw_data/DharmicData")
    private val jsonOutputDir = File("raw-data-processor/src/main/assets")
    private val dbOutputDir = File("app/src/main/assets/databases")

    private fun processFolder(folderName: String, mapper: (File) -> Unit) {
        val folder = File(inputDir, folderName)
        if (!folder.exists() || !folder.isDirectory) {
            println("[SKIP] $folderName: Folder not found.")
            return
        }

        println("[START] Processing folder: $folderName")

        val jsonFiles = folder.listFiles { file -> file.extension == "json" } ?: emptyArray()
        if (jsonFiles.isEmpty()) {
            println("[INFO] No JSON files found in $folderName")
            return
        }

        jsonFiles.forEach { file ->
            println("[FILE] Processing ${folderName}/${file.name}...")
            try {
                mapper(file)  // The lambda will write each verse directly to the output file
            } catch (e: Exception) {
                println("[ERROR] Failed to process ${file.name}: ${e.message}")
                e.printStackTrace()
            }
        }

        println("[DONE] Finished folder: $folderName")
    }

    fun run() {
        if (!jsonOutputDir.exists()) jsonOutputDir.mkdirs()
        val jsonOutputFile = File(jsonOutputDir, "sanatan_texts.json")

        if(!dbOutputDir.exists()) dbOutputDir.mkdirs()
        val dbOutputFile = File(dbOutputDir, "sanatan_texts.db")


        println("[START] Writing master texts to ${jsonOutputFile.absolutePath}")

        jsonOutputFile.printWriter().use { writer ->
            writer.println("[") // Start JSON array
            var first = true

            fun writeVerse(verse: Verse) {
                if (!first) writer.println(",") else first = false
                writer.print(json.encodeToString(verse))
            }

            // ----------------- Ramcharitmanas -----------------
            processFolder("Ramcharitmanas") { file ->
                val data = json.decodeFromString<List<RamcharitmanasDTO>>(file.readText())
                println("[INFO] Processing ${file.name}, ${data.size} verses")
                data.forEachIndexed { i, it ->
                    val verse = Verse(
                        bookName = "रामचरितमानस",
                        majorDivision = it.kaand,
                        verseIdentifier = "${i+1}",
                        originalText = it.content,
                        metadata = json.encodeToString(mapOf(
                            "type" to it.type,
                            "source_file" to file.path
                        ))
                    )
                    writeVerse(verse)
                }
            }

            // ----------------- Valmiki Ramayana -----------------
            val kaandaMap = mapOf(
                "balakanda" to "बालकाण्ड",
                "ayodhyakanda" to "अयोध्याकाण्ड",
                "aranyakanda" to "अरण्यकाण्ड",
                "kishkindhakanda" to "किष्किन्धाकाण्ड",
                "sundarakanda" to "सुन्दरकाण्ड",
                "yudhhakanda" to "युद्धकाण्ड",
                "uttarakanda" to "उत्तरकाण्ड"
            )
            processFolder("ValmikiRamayana") { file ->
                val data = json.decodeFromString<List<ValmikiRamayanaDTO>>(file.readText())
                println("[INFO] Processing ${file.name}, ${data.size} verses")
                data.forEach {
                    val verse = Verse(
                        bookName = "वाल्मीकि रामायण",
                        majorDivision = kaandaMap[it.kaanda],
                        minorDivision = "सर्ग ${it.sarg}",
                        verseIdentifier = "श्लोक ${it.shloka}",
                        originalText = it.text,
                        metadata = json.encodeToString(mapOf(
                            "kaanda" to it.kaanda,
                            "source_file" to file.path
                        ))
                    )
                    writeVerse(verse)
                }
            }

            // ----------------- Rigveda -----------------
            processFolder("Rigveda") { file ->
                val data = json.decodeFromString<List<RigvedaDTO>>(file.readText())
                println("[INFO] Processing ${file.name}, ${data.size} verses")
                data.forEach {
                    val verse = Verse(
                        bookName = "ऋग्वेद",
                        majorDivision = "मण्डल ${it.mandala}",
                        minorDivision = "सूक्त ${it.sukta}",
                        originalText = it.text,
                        metadata = json.encodeToString(mapOf(
                            "source_file" to file.path
                        ))
                    )
                    writeVerse(verse)
                }
            }

            // ----------------- Srimad Bhagavad Gita -----------------
            processFolder("SrimadBhagvadGita") { file ->
                val data = json.decodeFromString<SrimadBhagvadGitaWrapper>(file.readText())
                println("[INFO] Processing ${file.name}, ${data.bhagavadGitaChapter.size} verses")
                data.bhagavadGitaChapter.forEach {
                    val verse = Verse(
                        bookName = "श्रीमद भागवत गीता",
                        majorDivision = "अध्याय ${it.chapter}",
                        minorDivision = "श्लोक ${it.verse}",
                        originalText = it.text,
                        commentariesJson = json.encodeToString(it.commentaries),
                        translationsJson = json.encodeToString(it.translations),
                        metadata = json.encodeToString(mapOf(
                            "source_file" to file.path
                        ))
                    )
                    writeVerse(verse)
                }
            }

            // ----------------- Mahabharata (Critical Edition) -----------------
            processFolder("Mahabharata/Critical Edition") { file ->
                val jsonString = file.readText()
                val dataMap = json.decodeFromString<Map<String, MahabharataValueDTO>>(jsonString)
                println("[INFO] Processing ${file.name}, ${dataMap.size} lines")

                dataMap.forEach { (key, value) ->
                    val bookNum = key.substring(0, 2).toInt()
                    val chapterNum = key.substring(2, 5).toInt()
                    val verseNum = key.substring(5, 8).toInt()
                    val halfLetter = key.getOrNull(8)

                    val (verseIdentifier, isVerse) = when (halfLetter) {
                        'a' -> "श्लोक $verseNum • प्रथम अर्ध" to true
                        'c' -> "श्लोक $verseNum • द्वितीय अर्ध" to true
                        null -> "अनुच्छेद / संवाद" to false
                        else -> "अनुच्छेद / संवाद" to false
                    }

                    val parvaName = MahabharataParva.fromId(bookNum)
                    val adhyayaName = "अध्याय $chapterNum"

                    val metadataJson = buildJsonObject {
                        put("raw_key", key)
                        put("iast", value.text.ur)
                        put("ascii", value.text.ascii)
                        put("isVerse", isVerse)
                    }

                    val verse = Verse(
                        bookName = "महाभारत",
                        majorDivision = parvaName,
                        minorDivision = adhyayaName,
                        verseIdentifier = verseIdentifier,
                        originalText = value.text.ud,
                        metadata = metadataJson.toString()
                    )
                    writeVerse(verse)
                }
            }

            val atharvaSamhitaMap = mapOf(
                "shaunak" to "शौनक",
                "paippalada" to "पैप्पलाद"
            )

            processFolder("AtharvaVeda") { file ->
                val data = json.decodeFromString<List<AtharvaVedaDTO>>(file.readText())
                println("[INFO] Processing ${file.name}, ${data.size} verses")

                data.forEach {
                    val verse = Verse(
                        bookName = "अथर्ववेद",
                        majorDivision = "${atharvaSamhitaMap[it.samhita] ?: it.samhita} • काण्ड ${it.kaanda}",
                        minorDivision = "सूक्त ${it.sukta}",
                        originalText = it.text,
                        metadata = json.encodeToString(mapOf(
                            "samhita" to it.samhita,
                            "source_file" to file.path
                        ))
                    )
                    writeVerse(verse)
                }
            }

            val yajurvedaSamhitaMap = mapOf(
                "vajasneyi-kanva-samhita" to "वाजसनेयी काण्व",
                "vajasneyi-madhyandina-samhita" to "वाजसनेयी मध्यदीन"
            )

            processFolder("Yajurveda/kanva") { file ->
                val data = json.decodeFromString<List<YajurvedaKanvaDTO>>(file.readText())
                println("[INFO] Processing ${file.name}, ${data.size} verses")

                data.forEach {
                    val verse = Verse(
                        bookName = "यजुर्वेद",
                        majorDivision = yajurvedaSamhitaMap[it.samhita] ?: it.samhita,
                        minorDivision = "अध्याय ${it.chapter}",
                        originalText = it.text,
                        metadata = json.encodeToString(mapOf(
                            "samhita" to it.samhita,
                            "source_file" to file.path
                        ))
                    )
                    writeVerse(verse)
                }
            }

            processFolder("Yajurveda/madhyandina") { file ->
                val data = json.decodeFromString<List<YajurvedaMadhyandinaDTO>>(file.readText())
                println("[INFO] Processing ${file.name}, ${data.size} verses")

                data.forEach {
                    val verse = Verse(
                        bookName = "यजुर्वेद",
                        majorDivision = yajurvedaSamhitaMap[it.samhita] ?: it.samhita,
                        minorDivision = "अध्याय ${it.adhyaya}",
                        originalText = it.text,
                        metadata = json.encodeToString(mapOf(
                            "samhita" to it.samhita,
                            "source_file" to file.path
                        ))
                    )
                    writeVerse(verse)
                }
            }

            writer.println("\n]") // End JSON array
        }

        println("[SUCCESS] Master texts written to ${jsonOutputFile.absolutePath}")

        println("[INFO] Creating database and writting to ${dbOutputFile.absolutePath}")
        convertJsonToSqlite(jsonOutputFile, dbOutputFile)
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun convertJsonToSqlite(jsonFile: File, dbFile: File) {
        if (dbFile.exists()) dbFile.delete()

        DriverManager.getConnection("jdbc:sqlite:${dbFile.absolutePath}").use { conn ->
            conn.autoCommit = false

            val setupStmt = conn.createStatement()
            setupStmt.execute("""
            CREATE TABLE IF NOT EXISTS verses (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                bookName TEXT NOT NULL,
                majorDivision TEXT,
                minorDivision TEXT,
                verseIdentifier TEXT,
                originalText TEXT NOT NULL,
                commentariesJson TEXT,
                translationsJson TEXT,
                metadata TEXT
            )
        """)

            val sql = "INSERT INTO verses (bookName, majorDivision, minorDivision, verseIdentifier, originalText, commentariesJson, translationsJson, metadata) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
            val pstmt = conn.prepareStatement(sql)

            println("Reading $jsonFile into database...")
            val inputStream = FileInputStream(jsonFile)

            val verses = json.decodeFromStream<List<Verse>>(inputStream)

            verses.forEachIndexed { index, verse ->
                pstmt.setString(1, verse.bookName)
                pstmt.setString(2, verse.majorDivision)
                pstmt.setString(3, verse.minorDivision)
                pstmt.setString(4, verse.verseIdentifier)
                pstmt.setString(5, verse.originalText)
                pstmt.setString(6, verse.commentariesJson)
                pstmt.setString(7, verse.translationsJson)
                pstmt.setString(8, verse.metadata)
                pstmt.addBatch()

                if (index % 1000 == 0) {
                    pstmt.executeBatch()
                }
            }

            pstmt.executeBatch()
            conn.commit()

            // TODO: Work on this to make it better.
            setupStmt.execute("CREATE INDEX idx_search ON verses(bookName, majorDivision)")

            println("Database generation complete: ${dbFile.length() / 1024 / 1024} MB")

            val masterStmt = conn.createStatement()
            masterStmt.execute("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY, identity_hash TEXT)")

            val myHash = "eb1be500c007349304f9806590a3a4fe" // Identity hash from AppDatabase_Impl.kt
            masterStmt.execute("INSERT OR REPLACE INTO room_master_table (id, identity_hash) VALUES(42, '$myHash')")

            conn.commit()
        }
    }
}