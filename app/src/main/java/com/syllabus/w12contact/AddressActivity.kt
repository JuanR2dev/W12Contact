package com.syllabus.w12contact

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class AddressActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)

        val userName = intent.getStringExtra("user_name")
        val userPhotoId = intent.getIntExtra("user_photo_id", R.drawable.ic_user)

        val imgAvatar = findViewById<ImageView>(R.id.img_avatar)
        val txtUsername = findViewById<TextView>(R.id.txt_username)

        userName.also { txtUsername.text = it }
        userPhotoId.also { imgAvatar.setImageResource(it) }

    }
}