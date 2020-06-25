package com.novachevskyi.pokemon_mvi.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.novachevskyi.pokemon_mvi.R

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, newFragmentInstance())
                .commitAllowingStateLoss()
        }
    }

    private fun newFragmentInstance(): ListFragment {
        val args = Bundle()
        val fragment = ListFragment()
        fragment.arguments = args
        return fragment
    }
}
