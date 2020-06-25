package com.novachevskyi.pokemon_mvi.presentation

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableList
import com.novachevskyi.pokemon_mvi.data.models.PokemonListItem
import com.ww.roxie.BaseState

data class State(
    val items: ObservableList<PokemonListItem> = ObservableArrayList(),
    var isIdle: Boolean = false,
    val isLoading: ObservableBoolean = ObservableBoolean(false),
    val isError: ObservableBoolean = ObservableBoolean(false)
) : BaseState
