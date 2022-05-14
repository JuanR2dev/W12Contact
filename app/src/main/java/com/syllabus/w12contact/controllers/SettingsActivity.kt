package com.syllabus.w12contact.controllers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.syllabus.w12contact.R
import com.syllabus.w12contact.models.User
import com.syllabus.w12contact.models.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class SettingsActivity : AppCompatActivity() {

    private val scope by lazy { CoroutineScope(SupervisorJob()) }
    private val dao by lazy { UserDatabase.getInstance(this).userDao }
    private var user: User? = null

    private val prefEditor by lazy { getSharedPreferences("syllabus_prefs", MODE_PRIVATE).edit() }

    private var colorOption: Int
        get() = getSharedPreferences("syllabus_prefs", MODE_PRIVATE).getInt("colorop", 0)
        set(value) {
            prefEditor.putInt("colorop", value)
        }

    private val txtUsername by lazy { findViewById<TextView>(R.id.txt_username) }

    private val imgAvatar by lazy { findViewById<ImageView>(R.id.img_avatar) }

    private val edtStreet by lazy { findViewById<EditText>(R.id.edt_street) }
    private val edtExtern by lazy { findViewById<EditText>(R.id.edt_extern) }
    private val edtIntern by lazy { findViewById<EditText>(R.id.edt_intern) }
    private val edtColony by lazy { findViewById<EditText>(R.id.edt_colony) }
    private val edtCity by lazy { findViewById<EditText>(R.id.edt_city) }
    private val edtState by lazy { findViewById<EditText>(R.id.edt_state) }
    private val edtCountry by lazy { findViewById<EditText>(R.id.edt_country) }
    private val edtPostal by lazy { findViewById<EditText>(R.id.edt_postal) }

    private val btnSave by lazy { findViewById<Button>(R.id.btn_save) }

    private val btnColorA by lazy { findViewById<ImageButton>(R.id.btn_colorA) }
    private val btnColorB by lazy { findViewById<ImageButton>(R.id.btn_colorB) }
    private val btnColorC by lazy { findViewById<ImageButton>(R.id.btn_colorC) }

    private val uiroot by lazy { findViewById<View>(R.id.root) }

    private fun applyColorOption(option: Int) {
        val color = when (option) {
            1 -> R.color.colorB
            2 -> R.color.colorC
            else -> R.color.colorA
        }
        colorOption = option
        uiroot.setBackgroundResource(color)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        user = intent.getParcelableExtra("user")
        txtUsername.text = user?.getFormatName()

        imgAvatar.setImageResource(R.drawable.avatar_example)

        user?.address?.apply {
            edtStreet.setText(street)
            edtIntern.setText(intern)
            edtExtern.setText(extern)
            edtColony.setText(colony)
            edtCity.setText(city)
            edtState.setText(state)
            edtColony.setText(country)
            edtPostal.setText(postalCode)
        }

        btnSave.setOnClickListener {
            user?.address?.apply {
                street = edtStreet.text.toString()
                intern = edtIntern.text.toString()
                extern = edtExtern.text.toString()
                colony = edtColony.text.toString()
                city = edtCity.text.toString()
                state = edtState.text.toString()
                country = edtCountry.text.toString()
                postalCode = edtPostal.text.toString()
            }
            user?.let { scope.launch { dao.update(it) } }
            prefEditor.commit()
            Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show()
        }

        btnColorA.setOnClickListener { applyColorOption(0) }
        btnColorB.setOnClickListener { applyColorOption(1) }
        btnColorC.setOnClickListener { applyColorOption(2) }

    }

    override fun onResume() {
        super.onResume()
        applyColorOption(colorOption)
    }

}