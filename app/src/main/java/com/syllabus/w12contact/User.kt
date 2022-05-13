package com.syllabus.w12contact

import android.os.Parcel
import android.os.Parcelable
import androidx.room.*

enum class Gender(value: Int) {
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
    var address: Address? = null,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        enumValueOf(parcel.readString() ?: Gender.Genderless.name),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Address::class.java.classLoader) ?: Address(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(lastname)
        parcel.writeInt(age)
        parcel.writeString(gender.name)
        parcel.writeString(email)
        parcel.writeString(phoneNumber)
        parcel.writeParcelable(address, flags)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}

@Dao
interface UserDao {

    @Insert()
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