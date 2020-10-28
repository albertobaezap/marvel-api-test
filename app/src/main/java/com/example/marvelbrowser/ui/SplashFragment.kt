package com.example.marvelbrowser.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.marvelbrowser.R
import com.example.marvelbrowser.data.CharacterStoreController
import com.example.marvelbrowser.databinding.SplashFragmentBinding
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

/**
 * Splash screen for the app.
 */
class SplashFragment : Fragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()

    private lateinit var binding: SplashFragmentBinding

    private val characterStoreController: CharacterStoreController by instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        SplashFragmentBinding.inflate(inflater, container, false).apply { binding = this }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        characterStoreController.getCharacterListLiveData().observe(viewLifecycleOwner) { characterList ->

            characterList?.let {
                findNavController().navigate(
                    SplashFragmentDirections.toMainFragment(),
                    //Specify popup to inclusive here to avoid overriding the back button
                    NavOptions.Builder().setPopUpTo(R.id.splash_fragment, true).build()
                )
            } ?: run {
                Toast.makeText(
                    requireContext(),
                    "There was an error retrieving the list, please check your credentials again!",
                    Toast.LENGTH_LONG
                ).show()
            }


        }
    }

}