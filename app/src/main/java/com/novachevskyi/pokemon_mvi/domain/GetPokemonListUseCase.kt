package com.novachevskyi.pokemon_mvi.domain

import android.content.Context
import com.novachevskyi.pokemon_mvi.R
import com.novachevskyi.pokemon_mvi.data.PokemonDataSource
import com.novachevskyi.pokemon_mvi.data.models.PokemonListResponse
import io.reactivex.Flowable
import io.reactivex.Single

class GetPokemonListUseCase(
    private val pokemonDataSource: PokemonDataSource,
    private val context: Context
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
                        .map {
                            val stats = arrayListOf<String>()
                            item.detail?.stats?.forEach { statItem ->
                                stats.add(
                                    context.getString(
                                        R.string.stat_item,
                                        statItem.stat.name,
                                        statItem.base_stat,
                                        statItem.effort
                                    )
                                )
                            }
                            item.stats = stats
                            item
                        }
                        .map {
                            val types = arrayListOf<String>()
                            item.detail?.types?.forEach { typeItem ->
                                types.add(
                                    context.getString(
                                        R.string.type_item,
                                        typeItem.type.name
                                    )
                                )
                            }
                            item.types = types
                            item
                        }
                }.toList()
                .map {
                    response.results = it
                    response
                }
        }
}
