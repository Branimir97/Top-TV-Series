package branimirbutkovic.ferit.toptvseries.networking

import branimirbutkovic.ferit.toptvseries.models.Serie
import com.google.gson.annotations.SerializedName

data class APIResponse (
    @SerializedName("results")
    //polje serija koje se dohvaća iz results-a
    val series: List<Serie>
)
