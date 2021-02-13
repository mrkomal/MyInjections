package com.example.myinjections.room.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface InjectionsDao {
    @Query("SELECT * FROM injections_table")
    fun getAllInfo(): Flow<List<InjectionInfo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(injectionInfo: InjectionInfo)

    @Delete
    suspend fun delete(injectionInfo: InjectionInfo)
}