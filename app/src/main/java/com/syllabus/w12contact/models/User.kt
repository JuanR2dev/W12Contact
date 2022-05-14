package com.syllabus.w12contact.models

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.room.*
import com.syllabus.w12contact.R

enum class Gender(value: Int) {
    Genderless(0),
    Male(1),
    Female(2)
}

@Entity
data class User(
    var firstname: String,
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
        parcel.writeString(firstname)
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

    fun getFormatName() = "$firstname $lastname"
    fun getFormatGenderSummary(context:Context) : String {
        val strYears = context.getString(R.string.years)
        val strGender = when(gender) {
            Gender.Genderless -> "..."
            Gender.Male -> context.getString(R.string.male)
            Gender.Female -> context.getString(R.string.female)
        }
        return "$age $strYears $strGender"
    }
}

@Dao
interface UserDao {

    @Insert()
    suspend fun insert(user: User)

    @Query("SELECT * FROM User WHERE id = :id")
    fun getById(id: Int): LiveData<User?>

    @Query("SELECT * FROM User")
    fun getAll(): LiveData<List<User>>

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

}