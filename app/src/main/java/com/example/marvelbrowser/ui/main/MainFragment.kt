package com.example.marvelbrowser.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelbrowser.R
import com.example.marvelbrowser.databinding.MainFragmentBinding
import com.example.marvelbrowser.di.mainViewModel
import com.example.marvelbrowser.ui.adapter.CharacterListAdapter
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import timber.log.Timber

class MainFragment : Fragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()

    private val viewModel: MainViewModel by mainViewModel()

    private lateinit var binding: MainFragmentBinding
    private lateinit var characterListAdapter: CharacterListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        MainFragmentBinding.inflate(inflater, container, false).apply { binding = this }.root

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupViews()

        listenToCharacterList()
    }

    private fun setupViews() {
        with(binding) {
            characterListAdapter = CharacterListAdapter(requireContext()) {
                val bundle = bundleOf("characterId" to it)
                findNavController().navigate(R.id.to_detail_fragment, bundle)
            }

            characterList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (characterList.getChildAt(0).bottom
                        <= (characterList.height + characterList.scrollY)
                    ) {
                        //If we scroll too fast to the botton, we stop receiving updates.
                        characterListAdapter.notifyDataSetChanged()
                    }
                }
            })
            characterList.adapter = characterListAdapter
            characterList.addItemDecoration(DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL))
        }
    }

    private fun listenToCharacterList() =
        viewModel.getCharacterLiveData().observe(viewLifecycleOwner, { list ->
            list?.let {
                characterListAdapter.characterList = it
            } ?: Timber.w("List was null!")
        })
}