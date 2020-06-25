package com.novachevskyi.pokemon_mvi.presentation

import com.ww.roxie.BaseAction

sealed class Action : BaseAction {
    object LoadList : Action()
}
