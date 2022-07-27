package wahyu.ristanto.takehometest.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import wahyu.ristanto.takehometest.BuildConfig
import wahyu.ristanto.takehometest.data.model.BeritaResponse

interface BaseApi {
    companion object {
        const val BASE_URL = BuildConfig.BASE_API_URL
    }

//    https://newsapi.org/v2/everything?q=tesla&apiKey=fc269efae9894762aecb2d7e50d4b44b
    @GET("everything?apiKey=fc269efae9894762aecb2d7e50d4b44b")
    suspend fun searchBerita(
        @Query("q") query: String,
        @Query("page") position: Int
    ): BeritaResponse
}