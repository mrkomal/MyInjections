package com.example.myinjections.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myinjections.room.model.InjectionInfo
import com.example.myinjections.room.model.InjectionsDao

@Database(entities = [InjectionInfo::class], version = 1)
abstract class InjectionsDatabase : RoomDatabase() {

    abstract fun injectionsDao(): InjectionsDao

    companion object{
        private var databaseInstance: InjectionsDatabase ?= null

        fun getInstance(context: Context): InjectionsDatabase{
            return databaseInstance ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    InjectionsDatabase::class.java,
                    "injection_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                databaseInstance = instance
                instance
            }
        }
    }
}