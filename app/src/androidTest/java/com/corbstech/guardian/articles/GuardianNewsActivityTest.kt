package com.corbstech.guardian.articles

import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.corbstech.guardian.app.GuardianNewsActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GuardianNewsActivityTest {
    @get:Rule
    val testRule = ActivityScenarioRule(GuardianNewsActivity::class.java)

    @Test
    fun testActivityCreated() {
        val scenario = testRule.scenario
        scenario.moveToState(Lifecycle.State.CREATED)
    }
}
