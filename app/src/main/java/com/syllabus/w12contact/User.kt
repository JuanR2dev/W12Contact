package com.syllabus.w12contact

import androidx.room.*

enum class Gender(value:Int) {
    Genderless(0),
    Male(1),
    Female(2)
}

@Entity
data class User(
    var name: String,
    var lastname: String,
    var age: Int,
    var gender: Gender = Gender.Genderless,
    var email: String? = null,
    var phoneNumber: String? = null,
    @Embedded
    var address : Address? = null,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

@Dao
interface UserDao {

    @Insert
    fun insert(user: User)

    @Query("SELECT * FROM User WHERE id = :id")
    fun getById(id: Int): User?

    @Query("SELECT * FROM User")
    fun getAll(): List<User>

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

}