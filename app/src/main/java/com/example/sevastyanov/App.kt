package com.example.sevastyanov

import android.app.Application
import androidx.room.Room
import com.example.sevastyanov.data.MainDatabase

class App : Application() {

    private var database: MainDatabase? = null
    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(this, MainDatabase::class.java, "database")
            .build()
    }

    fun getDatabase(): MainDatabase? {
        return database
    }

    companion object {
        var instance: App? = null
    }
}