package com.syllabus.w12contact

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class AddressActivity : AppCompatActivity() {

    private val scope = CoroutineScope(SupervisorJob())
    private val userDao by lazy { UserDatabase.getInstance(this).userDao }

    private val imgAvatar by lazy { findViewById<ImageView>(R.id.img_avatar) }
    private val txtUsername by lazy { findViewById<TextView>(R.id.txt_username) }

    private val btnSave by lazy { findViewById<Button>(R.id.btn_save) }
    private val user by lazy { intent.getParcelableExtra<User>("user_data") }

    private val edtStreet by lazy { findViewById<EditText>(R.id.edt_street) }
    private val edtExtern by lazy { findViewById<EditText>(R.id.edt_extern) }
    private val edtIntern by lazy { findViewById<EditText>(R.id.edt_intern) }

    private val edtColony by lazy<EditText?> { findViewById<EditText>(R.id.edt_colony) }
    private val edtCity by lazy { findViewById<EditText>(R.id.edt_city) }
    private val edtCountry by lazy { findViewById<EditText>(R.id.edt_country) }
    private val edtState by lazy { findViewById<EditText>(R.id.edt_state) }

    private val edtPostal by lazy { findViewById<EditText>(R.id.edt_postal) }

    private suspend fun saveUserData() {
        user?.address?.also {
            user?.address?.street = edtStreet.text.toString()
            user?.address?.extern = edtExtern.text.toString()
            user?.address?.intern = edtIntern.text.toString()

            user?.address?.colony = edtColony?.text.toString()
            user?.address?.city = edtCity.text.toString()
            user?.address?.country = edtCountry.text.toString()
            user?.address?.state = edtState.text.toString()

            user?.address?.postalCode = edtPostal.text.toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)

        "${user?.name} ${user?.lastname}".also { txtUsername.text = it }
        imgAvatar.setImageResource(R.drawable.avatar_example)

        btnSave.setOnClickListener {
            scope.launch { saveUserData() }
        }

        user?.address?.also {
            edtStreet.setText(it.street)
            edtExtern.setText(it.extern)
            edtIntern.setText(it.intern)

            edtColony?.setText(it.colony)
            edtCity.setText(it.city)
            edtCountry.setText(it.country)
            edtState.setText(it.state)

            edtPostal.setText(it.postalCode)
        }

        btnSave.setOnClickListener { scope.launch { saveUserData() } }

    }
}