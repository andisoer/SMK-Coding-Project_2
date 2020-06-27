package com.soerjdev.smkcodingproject2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.soerjdev.smkcodingproject2.database.dao.ProvinsiCasesDao
import com.soerjdev.smkcodingproject2.database.model.ProvinsiCases
import kotlinx.coroutines.CoroutineScope

@Database(
    entities = arrayOf(ProvinsiCases::class),
    version = 1,
    exportSchema = true
)
abstract class AppLocalDatabase : RoomDatabase() {

    abstract fun provinsiCasesDao(): ProvinsiCasesDao

    companion object {

        @Volatile
        private var INSTANCE: AppLocalDatabase? = null

        fun getDatabase(context: Context): AppLocalDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppLocalDatabase::class.java,
                    "app_local_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }

}