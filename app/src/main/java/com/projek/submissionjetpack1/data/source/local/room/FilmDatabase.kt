package com.projek.submissionjetpack1.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.projek.submissionjetpack1.data.source.local.entity.Casting
import com.projek.submissionjetpack1.data.source.local.entity.FilmEntity
import com.projek.submissionjetpack1.data.source.local.entity.GenreEntity

@Database(
    entities = [Casting::class, FilmEntity::class, GenreEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FilmDatabase :RoomDatabase() {
    abstract fun filmDao():FilmDao

    companion object{
        @Volatile
        private var INSTANCE:FilmDatabase?=null

        fun getInstance(context: Context):FilmDatabase{
            return INSTANCE?: synchronized(this){
                Room.databaseBuilder(
                    context.applicationContext,
                    FilmDatabase::class.java,
                    "Film.db"
                ).build().apply {
                    INSTANCE=this
                }
            }
        }
    }
}