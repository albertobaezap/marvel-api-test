package com.example.marvelbrowser.presentation.main

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
import com.example.marvelbrowser.domain.entities.Character
import com.example.marvelbrowser.presentation.adapter.CharacterListAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein

/**
 * App's main fragment that will show a list of characters.
 */
class MainFragment : Fragment(), MainPresenter.View, KodeinAware {

    companion object {
        const val CHARACTER_ID_ARG = "characterId"
    }

    override val kodein: Kodein by closestKodein()

    private lateinit var binding: MainFragmentBinding
    private lateinit var characterListAdapter: CharacterListAdapter
    private lateinit var presenter: MainPresenter

    private val scope = CoroutineScope(Job())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        MainFragmentBinding.inflate(inflater, container, false).apply { binding = this }.root

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupViews()
        presenter = MainPresenter(requireContext(), this)
        presenter.loadCharacterList()
    }

    /**
     * Populate all the necessary views.
     */
    private fun setupViews() {
        with(binding) {
            characterListAdapter = CharacterListAdapter {
                //On item click navigate to the detail fragment for that character.
                val bundle = bundleOf(CHARACTER_ID_ARG to it)
                findNavController().navigate(R.id.to_detail_fragment, bundle)
            }

            /**
             * Since we're using a dynamic list, the diff will behave strangely if we scroll to the bottom while the list is still
             * being updated, so we can force an update if we reach the bottom in order to retrieve the proper list again.
             */
            /*characterList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (characterList.getChildAt(0).bottom
                        <= (characterList.height + characterList.scrollY)
                    ) {
                        //If we scroll too fast to the botton, we stop receiving updates.
                        characterListAdapter.notifyDataSetChanged()
                    }
                }
            })*/
            characterList.adapter = characterListAdapter
            characterList.addItemDecoration(DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL))
        }
    }

    /**
     * Listen to changes in the character list.
     */
    override fun displayCharacterList(characterList: List<Character>) {
        scope.launch(Dispatchers.Main) {
            characterListAdapter.characterList = characterList
        }
    }
}