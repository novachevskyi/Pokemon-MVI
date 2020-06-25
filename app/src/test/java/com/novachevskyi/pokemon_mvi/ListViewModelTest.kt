package com.novachevskyi.pokemon_mvi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.inOrder
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import com.novachevskyi.pokemon_mvi.data.models.PokemonListItem
import com.novachevskyi.pokemon_mvi.data.models.PokemonListResponse
import com.novachevskyi.pokemon_mvi.domain.GetPokemonListUseCase
import com.novachevskyi.pokemon_mvi.presentation.Action
import com.novachevskyi.pokemon_mvi.presentation.ListViewModel
import com.novachevskyi.pokemon_mvi.presentation.State
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ListViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testSchedulerRule = RxTestSchedulerRule()

    private lateinit var testSubject: ListViewModel

    private val idleState = State(isIdle = true)

    private val loadingState = State(isLoading = ObservableBoolean(true))

    private val listUseCase = mock<GetPokemonListUseCase>()

    private val observer = mock<Observer<State>>()

    @Before
    fun setUp() {
        testSubject = ListViewModel(idleState, listUseCase)
        testSubject.observableState.observeForever(observer)
    }

    @Test
    fun `Given pokemon list successfully loaded, when action LoadList is received, then State contains pokemon list`() {
        // GIVEN
        val pokemonList =
            listOf(PokemonListItem("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/"))
        val successState =
            State(ObservableArrayList<PokemonListItem>().apply { addAll(pokemonList) })

        whenever(listUseCase.load()).thenReturn(
            Single.just(
                PokemonListResponse(
                    1,
                    results = pokemonList
                )
            )
        )

        // WHEN
        testSubject.dispatch(Action.LoadList)
        testSchedulerRule.triggerActions()

        // THEN
        inOrder(observer) {
            verify(observer).onChanged(loadingState)
            verify(observer).onChanged(successState)
        }
        verifyNoMoreInteractions(observer)
    }

    @Test
    fun `Given pokemon list failed to load, when action LoadList is received, then State contains error`() {
        // GIVEN
        whenever(listUseCase.load()).thenReturn(Single.error(RuntimeException()))
        val errorState = State(isError = ObservableBoolean(true))

        // WHEN
        testSubject.dispatch(Action.LoadList)
        testSchedulerRule.triggerActions()

        // THEN
        inOrder(observer) {
            verify(observer).onChanged(loadingState)
            verify(observer).onChanged(errorState)
        }
        verifyNoMoreInteractions(observer)
    }
}