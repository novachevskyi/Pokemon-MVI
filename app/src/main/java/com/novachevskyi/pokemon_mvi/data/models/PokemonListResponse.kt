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
    var detail: PokemonDetail? = null,
    var stats: List<String>? = null,
    var types: List<String>? = null
) : Serializable

data class PokemonDetail(
    val name: String,
    val stats: List<PokemonStat>,
    val types: List<PokemonType>,
    val sprites: PokemonSprites
) : Serializable

data class PokemonStat(
    val base_stat: Int,
    val effort: Int,
    val stat: PokemonStatDetail
) : Serializable

data class PokemonStatDetail(
    val name: String,
    val url: String
) : Serializable

data class PokemonType(
    val slot: Int,
    val type: PokemonTypeDetail
) : Serializable

data class PokemonTypeDetail(
    val name: String,
    val url: String
) : Serializable

data class PokemonSprites(
    val back_default: String,
    val front_default: String
) : Serializable
