package branimirbutkovic.ferit.toptvseries.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import branimirbutkovic.ferit.toptvseries.models.Serie

@Dao
interface SerieDao {

    @Insert
    fun insert(serie: Serie)

    @Query("SELECT * FROM series")
    fun getAll(): List<Serie>

    @Query("SELECT * FROM series WHERE name = :serie_name")
    fun getSerie(serie_name: String): Serie

    @Query("DELETE FROM series WHERE name = :serie_name")
    fun deleteSerie(serie_name: String)
}