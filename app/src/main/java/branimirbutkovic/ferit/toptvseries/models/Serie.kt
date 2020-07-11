package branimirbutkovic.ferit.toptvseries.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "series")
data class Serie (
    @PrimaryKey (autoGenerate = true) val id: Int,

    @SerializedName("name")
    @ColumnInfo val name: String,

    @SerializedName("poster_path")
    @ColumnInfo val imgUrl : String,

    @SerializedName("overview")
    @ColumnInfo val overview: String
)