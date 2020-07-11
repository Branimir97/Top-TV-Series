package branimirbutkovic.ferit.toptvseries.networking

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkUtils {

    private val BASE_API_URL = "https://api.themoviedb.org/3/tv/"

    //ne može se provjeriti singleton ukoliko se retrofit postavi kao late init varijabla
    private var retrofit : Retrofit ?= null

    private val httpClient = OkHttpClient.Builder().build()

    private val gsonConverterFactory = GsonConverterFactory.create()

    private val client = createClient()

    //funkcija koja omogućuje kreiranje retrofita
    private fun createRetrofit() : Retrofit? {
        retrofit = Retrofit.Builder()
            .client(httpClient)
            .baseUrl(BASE_API_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
        return retrofit
    }

    //funkcija koja omogućuje kreiranje retrofita pomoću singleton obrasca
    private fun createClient() : Retrofit? {
        if(retrofit == null) {
            return createRetrofit()
        } else {
            return retrofit
        }
    }

    //funkcija za dohvat API servisa
    fun getAPIService() : SeriesAPIService {
        return client!!.create(SeriesAPIService::class.java)
    }
}