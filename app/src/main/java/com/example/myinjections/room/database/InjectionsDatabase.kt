package com.example.myinjections.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myinjections.room.model.InjectionInfo
import com.example.myinjections.room.model.InjectionsDao

@Database(entities = [InjectionInfo::class], version = 1)
abstract class InjectionsDatabase : RoomDatabase() {

    abstract fun injectionsDao(): InjectionsDao

}