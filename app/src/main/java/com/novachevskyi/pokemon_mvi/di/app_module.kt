package com.novachevskyi.pokemon_mvi.di

import com.novachevskyi.pokemon_mvi.domain.GetPokemonListUseCase
import com.novachevskyi.pokemon_mvi.presentation.details.DetailViewModel
import com.novachevskyi.pokemon_mvi.presentation.ListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        ListViewModel(
            null,
            get()
        )
    }

    viewModel {
        DetailViewModel()
    }

    single<GetPokemonListUseCase>(createdAtStart = true) { GetPokemonListUseCase(get(), get()) }
}
