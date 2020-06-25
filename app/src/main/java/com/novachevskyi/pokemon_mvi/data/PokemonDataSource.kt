package com.novachevskyi.pokemon_mvi.data

import com.novachevskyi.pokemon_mvi.data.models.PokemonDetail
import com.novachevskyi.pokemon_mvi.data.models.PokemonListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.Url

interface PokemonDataSource {

    @GET("/api/v2/pokemon")
    @Headers("Content-type: application/json")
    fun getList(
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 20
    ): Single<PokemonListResponse>

    @GET
    fun getDetail(@Url url: String): Single<PokemonDetail>

}
