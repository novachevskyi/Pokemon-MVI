package com.novachevskyi.pokemon_mvi.data.models

import java.io.Serializable

data class PokemonListResponse(
    val count: Int,
    val next: String? = null,
    val previous: String? = null,
    var results: List<PokemonListItem> = arrayListOf()
)

data class PokemonListItem(
    val name: String,
    val url: String,
    var detail: PokemonDetail? = null
) : Serializable

data class PokemonDetail(
    val name: String,
    val weight: Int
) : Serializable
