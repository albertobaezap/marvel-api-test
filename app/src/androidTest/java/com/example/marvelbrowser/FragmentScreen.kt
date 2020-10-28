package com.example.marvelbrowser

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.agoda.kakao.text.KTextView
import com.example.marvelbrowser.ui.main.DetailFragment
import com.example.marvelbrowser.util.FragmentTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

class FragmentScreen : Screen<FragmentScreen>() {
    val text = KTextView { withId(R.id.character_detail_name) }
}

@RunWith(AndroidJUnit4::class)
class FragmentTest {

    @Rule @JvmField
    val fragmentRule = FragmentTestRule(DetailFragment::class.java)

    @Test
    fun showItemName() {

        fragmentRule.launchActivity(null)

        onScreen<FragmentScreen> {
            text {
                hasText("Random superhero")
            }
        }
    }
}