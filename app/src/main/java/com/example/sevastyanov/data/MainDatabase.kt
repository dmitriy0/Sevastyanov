package com.example.sevastyanov.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sevastyanov.domain.entities.FilmInList
import com.example.sevastyanov.domain.entities.Genre

@Database(entities = [FilmInList::class, Genre::class], version = 1)
abstract class MainDatabase : RoomDatabase() {
    abstract fun dao(): Dao?
}