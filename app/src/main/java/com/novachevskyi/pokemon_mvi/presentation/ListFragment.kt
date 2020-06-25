package com.novachevskyi.pokemon_mvi.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.novachevskyi.pokemon_mvi.R
import com.novachevskyi.pokemon_mvi.databinding.FragmentListBinding
import com.novachevskyi.pokemon_mvi.presentation.DetailActivity.Companion.ITEM_PARAM_NAME
import org.koin.android.viewmodel.ext.android.viewModel

class ListFragment : Fragment() {

    private val viewModel: ListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentListBinding>(
            inflater,
            R.layout.fragment_list,
            container,
            false
        )
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.dispatch(Action.LoadList)

        viewModel.itemClick
            .doOnNext {
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra(ITEM_PARAM_NAME, it)
                startActivity(intent)
            }
            .subscribe()
    }
}
