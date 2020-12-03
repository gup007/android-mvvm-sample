/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.mvvm

import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.SavedStateHandle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.android.mvvm.home.BaseViewHolder
import com.android.mvvm.home.HomeViewModel
import org.hamcrest.Matcher
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class HomeActivityTest {

    @get:Rule
    val rule = ActivityScenarioRule(HomeActivity::class.java)

    private fun sleep(timeInMillis: Long) {
        try {
            Thread.sleep(timeInMillis)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    @Test
    fun scrollToItemBelowFold_checkItsText() {

        val viewModel = HomeViewModel(RecipeRepoRestTestImpl(), SavedStateHandle(), null)
        viewModel.fetchRecipeList()

        sleep(2000)

        Espresso.onView(withId(R.id.rv_recipe_list))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    ITEM_BELOW_THE_FOLD, ViewActions.click()
                )
            )
    }

    @Test
    fun edit() {
        // given
        val givenIdxWillBeEdit = 1
        val givenReplaceText = "replaced!!"

        val viewModel = HomeViewModel(RecipeRepoRestTestImpl(), SavedStateHandle(), null)
        viewModel.fetchRecipeList()
        sleep(2000)

        // when
        Espresso.onView(withId(R.id.rv_recipe_list))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<BaseViewHolder>(
                    givenIdxWillBeEdit,
                    object : ViewAction {
                        override fun getDescription(): String = "get before content"

                        override fun getConstraints(): Matcher<View> = ViewMatchers.isDisplayed()

                        override fun perform(uiController: UiController, view: View) {
                            view.findViewById<TextView>(R.id.tv_recipe_title).text =
                                givenReplaceText
                        }
                    })
            )


        // then
        Espresso.onView(withId(R.id.rv_recipe_list))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<BaseViewHolder>(
                    givenIdxWillBeEdit,
                    object : ViewAction {
                        override fun getDescription(): String = "check replaced text"

                        override fun getConstraints(): Matcher<View> = ViewMatchers.isDisplayed()

                        override fun perform(uiController: UiController, view: View) {
                            val content =
                                view.findViewById<TextView>(R.id.tv_recipe_title).text.toString()
                            Assert.assertEquals(givenReplaceText, content)
                        }
                    })
            )

    }

    companion object {
        private const val ITEM_BELOW_THE_FOLD = 1
    }
}