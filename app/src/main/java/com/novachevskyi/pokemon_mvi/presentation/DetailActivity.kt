package com.novachevskyi.pokemon_mvi.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.novachevskyi.pokemon_mvi.R
import com.novachevskyi.pokemon_mvi.data.models.PokemonListItem
import com.novachevskyi.pokemon_mvi.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val item = intent.getSerializableExtra(ITEM_PARAM_NAME) as? PokemonListItem
        val binding = DataBindingUtil.setContentView<ActivityDetailBinding>(
            this,
            R.layout.activity_detail
        )
        binding.item = item
        binding.executePendingBindings()
    }

    companion object {
        const val ITEM_PARAM_NAME = "item"
    }
}
