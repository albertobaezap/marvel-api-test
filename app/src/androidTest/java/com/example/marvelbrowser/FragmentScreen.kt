package com.example.marvelbrowser

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.example.marvelbrowser.presentation.detail.DetailFragment
import com.example.marvelbrowser.presentation.main.MainFragment.Companion.CHARACTER_ID_ARG
import com.example.marvelbrowser.util.FragmentTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

class FragmentScreen : Screen<FragmentScreen>() {
    val text = KTextView { withId(R.id.character_detail_name) }
}

@RunWith(AndroidJUnit4::class)
class FragmentTest {

    @Rule
    @JvmField
    val fragmentRule = FragmentTestRule(DetailFragment::class.java)

    @Test
    fun showItemName() {
        val scenario = launchFragmentInContainer<DetailFragment>(
            Bundle().apply {
                putInt(CHARACTER_ID_ARG, 0)
            }
        )
        onView(withId(R.id.character_detail_name)).check(matches(withText("Random superhero")))

    }
}