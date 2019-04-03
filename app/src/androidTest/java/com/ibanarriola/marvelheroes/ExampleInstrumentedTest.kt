package com.ibanarriola.marvelheroes


import androidx.recyclerview.widget.RecyclerView
import com.ibanarriola.marvelheroes.view.activity.HeroDetailActivity
import com.ibanarriola.marvelheroes.view.activity.MainActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import android.content.Intent
import junit.framework.Assert.assertTrue
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.android.controller.ActivityController


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(RobolectricTestRunner::class)
class ExampleInstrumentedTest {

    lateinit var activityController: ActivityController<MainActivity>
    lateinit var mainActivity: MainActivity

    @Before
    fun init() {
        activityController = Robolectric.buildActivity(MainActivity::class.java)
        activityController.create().start().resume()
        mainActivity = activityController.get()

    }

    @Test
    fun useAppContext() {
        val expectedIntent = Intent (mainActivity, HeroDetailActivity::class.java)
        val recyclerView = mainActivity.findViewById<RecyclerView>(R.id.heroes_list)
        recyclerView.findViewHolderForAdapterPosition(0)!!.itemView.performClick()
        val shadowActivity = Shadows.shadowOf(mainActivity)
        val actualIntent = shadowActivity.nextStartedActivity
        assertTrue(expectedIntent.filterEquals(actualIntent))
    }
}
