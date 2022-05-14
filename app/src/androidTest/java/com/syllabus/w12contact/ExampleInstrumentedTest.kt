package com.syllabus.w12contact

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.syllabus.w12contact.models.Address
import com.syllabus.w12contact.models.Gender
import com.syllabus.w12contact.models.User
import com.syllabus.w12contact.models.UserDatabase

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.syllabus.w12contact", appContext.packageName)
    }

    /*
    @Test
    fun userRoomCRUD() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        var user: User? = User(
            firstname = "Juan",
            lastname = "Chavez",
            age = 22,
            gender = Gender.Male,
            email = "juanjesus.chavez@r2devpros.com",
            phoneNumber = "8611241152",
            address = Address(
                street = "11 de Julio",
                extern = "2015",
                intern = null,
                colony = "Davila",
                city = "Nueva Rosita",
                state = "Coahuila",
                country = "Mexico",
                postalCode = "26870"
            )
        )
        val database = UserDatabase.getInstance(context = appContext)
        val dao = database.userDao
        dao.insert(user!!)
        user = dao.getById(id = 1).value
        assertNotNull(user)
        user?.address?.state = "Chihuahua"
        dao.update(user!!)
        user = dao.getById(id = 1).value
        assertEquals("Chihuahua", user?.address?.state)
    }
     */

    @Test
    fun userPreferencesUsage() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val preferences = appContext.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString("My custom preference", "My custom value");
        editor.apply()
        assertEquals("My custom value", preferences.getString("My custom preference", null))
        editor.putString("My custom preference", "My other custom value")
        editor.apply()
        assertEquals("My other custom value", preferences.getString("My custom preference", null))
    }

}