package com.syllabus.w12contact

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // A given person
        val user = User(
            name="Juan Jesús",
            lastname="Chávez Villa",
            age=22,
            gender=Gender.Male,
            email="janitochvvll2311@gmail.com",
            phoneNumber = "8611241152"
        )

        // Set visuals (supposed got from internet)
        val imgBanner = findViewById<ImageView>(R.id.img_banner)
        imgBanner.setImageResource(R.drawable.banner_example)

        val imgAvatar = findViewById<ImageView>(R.id.img_avatar)
        imgAvatar.setImageResource(R.drawable.avatar_example)

        // Set info

        val txtName = findViewById<TextView>(R.id.txt_name)
        "${user.name} ${user.lastname}".also { txtName.text = it }

        val txtGenderSummary = findViewById<TextView>(R.id.txt_gender_summary);
        "${user.age} ${getString(R.string.years)} ${when(user.gender){
            Gender.Male-> getString(R.string.male)
            Gender.Female-> getString(R.string.female)
            null -> ""
        }}".also { txtGenderSummary.text = it }

        val txtEmail = findViewById<TextView>(R.id.txt_email)
        txtEmail.text = user.email ?: "..."

        val txtPhoneNumber = findViewById<TextView>(R.id.txt_phone_number)
        txtPhoneNumber.text = user.phoneNumber ?: "..."

        val imgGender = findViewById<ImageView>(R.id.img_gender)
        when(user.gender) {
            Gender.Male -> imgGender.setImageResource(R.drawable.ic_mars)
            Gender.Female -> imgGender.setImageResource(R.drawable.ic_venus)
            null -> imgGender.setImageResource(R.drawable.ic_genderless)
        }

        val btnAddress = findViewById<Button>(R.id.btn_address)
        btnAddress.setOnClickListener {
            var intent = Intent(this, AddressActivity::class.java)
            "${user.name} ${user.lastname}".also{ intent.putExtra("user_name", it) }
            intent.putExtra("user_photo_id", R.drawable.avatar_example)
            startActivity(intent)
        }

    }
}