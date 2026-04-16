package com.example.unscramble.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    @Query("SELECT * FROM word_table ORDER BY originalWord ASC")
    fun getAllWords(): Flow<List<Word>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWord(word: Word)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(words: List<Word>)

    @Query("SELECT COUNT(*) FROM word_table WHERE originalWord = :word")
    suspend fun isWordExists(word: String): Int

    @Query("SELECT * FROM word_table ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomWord(): Word?
}