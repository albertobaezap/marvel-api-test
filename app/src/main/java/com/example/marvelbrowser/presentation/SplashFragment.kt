package com.example.marvelbrowser.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.marvelbrowser.R
import com.example.marvelbrowser.databinding.SplashFragmentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein

/**
 * Splash screen for the app.
 */
class SplashFragment : Fragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()

    private lateinit var binding: SplashFragmentBinding
    private val scope = CoroutineScope(Job())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        SplashFragmentBinding.inflate(inflater, container, false).apply { binding = this }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        scope.launch {
            delay(5000)
            findNavController().navigate(
                SplashFragmentDirections.toMainFragment(),
                //Specify popup to inclusive here to avoid overriding the back button
                NavOptions.Builder().setPopUpTo(R.id.splash_fragment, true).build()
            )
        }
    }
}