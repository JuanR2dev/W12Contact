package com.syllabus.w12contact

enum class Gender {
    Male,
    Female
}

data class User(
    var name:String,
    var lastname:String,
    var age:Int,
    var gender:Gender? = null,
    var email: String? = null,
    var phoneNumber: String? = null,
)