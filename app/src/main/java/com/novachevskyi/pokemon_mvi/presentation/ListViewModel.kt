package com.novachevskyi.pokemon_mvi.presentation

import com.novachevskyi.pokemon_mvi.BR
import com.novachevskyi.pokemon_mvi.R
import com.novachevskyi.pokemon_mvi.data.models.PokemonListItem
import com.novachevskyi.pokemon_mvi.domain.GetPokemonListUseCase
import com.ww.roxie.BaseViewModel
import com.ww.roxie.Reducer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.ofType
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import me.tatarka.bindingcollectionadapter2.ItemBinding

class ListViewModel(
    initialState: State?,
    private val loadPokemonListUseCase: GetPokemonListUseCase
) : BaseViewModel<Action, State>() {

    val itemClick: PublishSubject<PokemonListItem> = PublishSubject.create()

    val itemBinding = ItemBinding.of<PokemonListItem>(BR.item, R.layout.list_item)
        .bindExtra(BR.itemClick, itemClick)

    val viewState: State
        get() = state.value ?: initialState

    override val initialState = initialState ?: State(
        isIdle = true
    )

    private val reducer: Reducer<State, Change> = { state, change ->
        when (change) {
            is Change.Loading -> state.apply {
                isIdle = false
                isLoading = true
                isError = false
            }
            is Change.PokemonList -> state.apply {
                isLoading = false
                items.addAll(change.items)
            }
            is Change.Error -> state.apply {
                isLoading = false
                isError = true
            }
        }
    }

    init {
        bindActions()
    }

    private fun bindActions() {
        val loadPokemonInitialListChange = actions.ofType<Action.LoadList>()
            .switchMap {
                loadPokemonListUseCase.load()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .toObservable()
                    .map<Change> { Change.PokemonList(it.results) }
                    .defaultIfEmpty(Change.PokemonList(emptyList()))
                    .onErrorReturn { Change.Error(it) }
                    .startWith(Change.Loading)
            }

        disposables += loadPokemonInitialListChange
            .scan(initialState, reducer)
            .filter { !it.isIdle }
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(state::setValue)
    }
}
