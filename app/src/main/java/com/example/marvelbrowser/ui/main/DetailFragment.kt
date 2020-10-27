package com.example.marvelbrowser.ui.main

import androidx.fragment.app.Fragment
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein

class DetailFragment: Fragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()

}