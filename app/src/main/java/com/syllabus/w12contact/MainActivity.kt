package com.syllabus.w12contact

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private val scope = CoroutineScope(SupervisorJob())
    private val userDao by lazy { UserDatabase.getInstance(this).userDao }

    private val imgBanner by lazy { findViewById<ImageView>(R.id.img_banner) }
    private val imgAvatar by lazy { findViewById<ImageView>(R.id.img_avatar) }
    private val txtName by lazy { findViewById<TextView>(R.id.txt_name) }
    private val txtAgeInfo by lazy { findViewById<TextView>(R.id.txt_gender_summary) }
    private val txtEmail by lazy { findViewById<TextView>(R.id.txt_email) }
    private val txtPhoneNumber by lazy { findViewById<TextView>(R.id.txt_phone_number) }
    private val icGender by lazy { findViewById<ImageView>(R.id.img_gender) }
    private val btnSettings by lazy { findViewById<Button>(R.id.btn_settings) }

    private val rootLayout by lazy { findViewById<ConstraintLayout>(R.id.root) }

    fun applyColorOption() {
        val preferences = getPreferences(MODE_PRIVATE)
        when (preferences.getInt("theme_color", 0)) {
            0 -> rootLayout.setBackgroundResource(R.color.colorA)
            1 -> rootLayout.setBackgroundResource(R.color.colorB)
            2 -> rootLayout.setBackgroundResource(R.color.colorC)
        }
    }

    private suspend fun getUserData() {
        // A given person
        var user = userDao.getAll().lastOrNull() ?: User(
            name = "Juan Jesús",
            lastname = "Chávez Villa",
            age = 22,
            gender = com.syllabus.w12contact.Gender.Male,
            email = "janitochvvll2311@gmail.com",
            phoneNumber = "8611241152"
        )

        if (user.id == 0) {
            userDao.insert(user)
        }

        // Set visuals (supposed got from internet)
        imgBanner.setImageResource(R.drawable.banner_example)
        imgAvatar.setImageResource(R.drawable.avatar_example)

        // Set info
        "${user.name} ${user.lastname}".also { txtName.text = it }
        "${user.age} ${getString(R.string.years)} ${
            when (user.gender) {
                Gender.Male -> getString(R.string.male)
                Gender.Female -> getString(R.string.female)
                else -> ""
            }
        }".also { txtAgeInfo.text = it }

        txtEmail.text = user.email ?: "..."
        txtPhoneNumber.text = user.phoneNumber ?: "..."

        when (user.gender) {
            Gender.Male -> icGender.setImageResource(R.drawable.ic_mars)
            Gender.Female -> icGender.setImageResource(R.drawable.ic_venus)
            else -> icGender.setImageResource(R.drawable.ic_genderless)
        }

        btnSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            intent.putExtra("user_data", user)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        applyColorOption()
        scope.launch { getUserData() }

    }
}