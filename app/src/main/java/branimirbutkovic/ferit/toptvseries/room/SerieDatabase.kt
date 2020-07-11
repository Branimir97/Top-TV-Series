package branimirbutkovic.ferit.toptvseries.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import branimirbutkovic.ferit.toptvseries.models.MyApplication
import branimirbutkovic.ferit.toptvseries.models.Serie

@Database(version = 1, entities = arrayOf(Serie::class))
abstract class SerieDatabase : RoomDatabase() {

    abstract fun serieDao() : SerieDao

    companion object {
        private const val NAME = "serie_database"
        private var INSTANCE: SerieDatabase? = null

        fun getInstance(): SerieDatabase {
            if(INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    MyApplication.ApplicationContext,
                    SerieDatabase::class.java,
                    NAME
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE as SerieDatabase
        }
    }
}