package branimirbutkovic.ferit.toptvseries.networking

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SeriesAPIService {
    @GET("popular")
    //funkcija za slanje requesta API-ju
    fun getPopularSeries(@Query("api_key") api_key: String) : Call<APIResponse>
}