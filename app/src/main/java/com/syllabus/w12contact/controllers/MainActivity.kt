package com.syllabus.w12contact.controllers

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.*
import com.syllabus.w12contact.R
import com.syllabus.w12contact.models.Address
import com.syllabus.w12contact.models.Gender
import com.syllabus.w12contact.models.User
import com.syllabus.w12contact.models.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val scope by lazy { CoroutineScope(SupervisorJob()) }
    private val dao by lazy { UserDatabase.getInstance(this).userDao }

    private val txtName by lazy { findViewById<TextView>(R.id.txt_name) }
    private val txtGenderSummary by lazy { findViewById<TextView>(R.id.txt_gender_summary) }
    private val txtEmail by lazy { findViewById<TextView>(R.id.txt_email) }
    private val txtPhoneNumber by lazy { findViewById<TextView>(R.id.txt_phone_number) }

    private val imgAvatar by lazy { findViewById<ImageView>(R.id.img_avatar) }
    private val imgBanner by lazy { findViewById<ImageView>(R.id.img_banner) }
    private val imgGender by lazy { findViewById<ImageView>(R.id.img_gender) }

    private val btnSettings by lazy { findViewById<Button>(R.id.btn_settings) }

    private val uiroot by lazy { findViewById<View>(R.id.root) }

    private fun applyColorOption() {
        val color = when (getSharedPreferences("syllabus_prefs", MODE_PRIVATE).getInt("colorop", 0)) {
            1 -> R.color.colorB
            2 -> R.color.colorC
            else -> R.color.colorA
        }
        uiroot.setBackgroundResource(color)
    }

    private fun bindData(user: LiveData<User?>) {
        user.observe(this, Observer {
            if (it == null) {
                scope.launch {
                    dao.insert(
                        // region User
                        User(
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
                        // endregion User
                    )
                }
                bindData(dao.getById(1))
            } else {
                txtName.text = it.getFormatName()
                txtGenderSummary.text = it.getFormatGenderSummary(this)
                txtEmail.text = it.email
                txtPhoneNumber.text = it.phoneNumber
                imgGender.setImageResource(when(it.gender){
                    Gender.Genderless -> R.drawable.ic_genderless
                    Gender.Male -> R.drawable.ic_mars
                    Gender.Female -> R.drawable.ic_venus
                })
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindData(dao.getById(1))
        imgAvatar.setImageResource(R.drawable.avatar_example)
        imgBanner.setImageResource(R.drawable.banner_example)

        btnSettings.setOnClickListener {
            val user = dao.getById(1)
            user.observe(this, Observer {
                val settingsIntent = Intent(applicationContext, SettingsActivity::class.java)
                settingsIntent.putExtra("user", it)
                startActivity(settingsIntent)
                user.removeObservers(this)
            })
        }

    }

    override fun onResume() {
        super.onResume()
        applyColorOption()
    }

}