package com.example.myinjections.room.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "injections_table")
data class InjectionInfo (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") @NonNull val name: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "dose", defaultValue = "Unknown") val dose: Double
)