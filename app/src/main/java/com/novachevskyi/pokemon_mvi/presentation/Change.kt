package com.novachevskyi.pokemon_mvi.presentation

import com.novachevskyi.pokemon_mvi.data.models.PokemonListItem

sealed class Change {
    object Loading : Change()
    data class PokemonList(val items: List<PokemonListItem>) : Change()
    data class Error(val throwable: Throwable?) : Change()
}
