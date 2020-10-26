package com.example.marvelbrowser.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.marvelbrowser.databinding.MainFragmentBinding
import com.example.marvelbrowser.di.mainViewModel
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein

class MainFragment : Fragment(), KodeinAware {

    companion object {
        fun newInstance() = MainFragment()
    }

    override val kodein: Kodein by closestKodein()

    private val viewModel: MainViewModel by mainViewModel()

    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View =
        MainFragmentBinding.inflate(inflater, container, false).apply { binding = this }.root

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loadList()
    }

    private fun loadList() {
        viewModel.getCharacterList()
    }

}