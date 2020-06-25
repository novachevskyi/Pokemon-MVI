package com.novachevskyi.pokemon_mvi.presentation.details

import androidx.lifecycle.ViewModel
import com.novachevskyi.pokemon_mvi.BR
import com.novachevskyi.pokemon_mvi.R
import me.tatarka.bindingcollectionadapter2.ItemBinding

class DetailViewModel : ViewModel() {
    val itemBinding = ItemBinding.of<String>(BR.item, R.layout.detail_list_item)
}
