package com.example.unscramble.data

import kotlinx.coroutines.flow.Flow

class WordRepository(private val wordDao: WordDao) {

    val allWords: Flow<List<Word>> = wordDao.getAllWords()

    suspend fun addWord(original: String, scrambled: String): Boolean {
        if (wordDao.isWordExists(original.uppercase()) > 0) {
            return false
        }
        val newWord = Word(
            originalWord = original.uppercase().trim(),
            scrambledWord = scrambled.uppercase().trim()
        )
        wordDao.insertWord(newWord)
        return true
    }

    suspend fun insertWord(word: Word) {
        wordDao.insertWord(word)
    }

    suspend fun getRandomWord(): Word? {
        return wordDao.getRandomWord()
    }
}