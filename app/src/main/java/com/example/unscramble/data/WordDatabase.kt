package com.example.unscramble.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Word::class],
    version = 1,
    exportSchema = false
)
abstract class WordDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao

    companion object {
        @Volatile
        private var INSTANCE: WordDatabase? = null

        fun getInstance(context: Context): WordDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordDatabase::class.java,
                    "word_database"
                )
                    .addCallback(WordDatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class WordDatabaseCallback : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        populateDatabase(database.wordDao())
                    }
                }
            }

            suspend fun populateDatabase(wordDao: WordDao) {
                val defaultWords = listOf(
                    Word(originalWord = "ANIMAL", scrambledWord = "LAMINA"),
                    Word(originalWord = "AUTO", scrambledWord = "TOUA"),
                    Word(originalWord = "ALPHABET", scrambledWord = "BETALPH"),
                    Word(originalWord = "BALLOON", scrambledWord = "LLABNOO"),
                    Word(originalWord = "BASKET", scrambledWord = "TEBASK"),
                    Word(originalWord = "CAMERA", scrambledWord = "RACAME"),
                    Word(originalWord = "CAMPING", scrambledWord = "PINGCAM"),
                    Word(originalWord = "ELEPHANT", scrambledWord = "PHANTELE"),
                    Word(originalWord = "GUITAR", scrambledWord = "TARGUI"),
                    Word(originalWord = "KANGAROO", scrambledWord = "AROOKANG")
                )
                wordDao.insertAll(defaultWords)
            }
        }
    }
}