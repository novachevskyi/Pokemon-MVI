package com.novachevskyi.pokemon_mvi.domain

import com.novachevskyi.pokemon_mvi.data.PokemonDataSource
import com.novachevskyi.pokemon_mvi.data.models.PokemonListResponse
import io.reactivex.Flowable
import io.reactivex.Single

class GetPokemonListUseCase(
    private val pokemonDataSource: PokemonDataSource
) {
    fun load(offset: Int = 0, limit: Int = 20): Single<PokemonListResponse> =
        pokemonDataSource.getList(offset, limit).flatMap { response ->
            Flowable.fromIterable(response.results)
                .flatMapSingle { item ->
                    pokemonDataSource.getDetail(item.url)
                        .map {
                            item.detail = it
                            item
                        }
                }.toList()
                .map {
                    response.results = it
                    response
                }
        }
}
