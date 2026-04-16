package com.example.unscramble.data

import android.content.Context

class AppContainer(context: Context) {
    private val database: WordDatabase = WordDatabase.getInstance(context)
    val repository: WordRepository = WordRepository(database.wordDao())
}