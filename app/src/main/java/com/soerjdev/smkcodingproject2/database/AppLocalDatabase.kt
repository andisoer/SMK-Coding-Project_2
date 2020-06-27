package com.soerjdev.smkcodingproject2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.soerjdev.smkcodingproject2.database.dao.GlobalCasesDao
import com.soerjdev.smkcodingproject2.database.dao.ProvinsiCasesDao
import com.soerjdev.smkcodingproject2.database.model.GlobalCases
import com.soerjdev.smkcodingproject2.database.model.ProvinsiCases
import kotlinx.coroutines.CoroutineScope

@Database(
    entities = [ProvinsiCases::class, GlobalCases::class],
    version = 2,
    exportSchema = true
)
abstract class AppLocalDatabase : RoomDatabase() {

    abstract fun provinsiCasesDao(): ProvinsiCasesDao
    abstract fun globalCasesDa() : GlobalCasesDao

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
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }

    }

}