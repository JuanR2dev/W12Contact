package com.syllabus.w12contact

import android.os.Parcel
import android.os.Parcelable

data class Address(
    var street: String? = null,
    var extern: String? = null,
    var intern: String? = null,
    var colony: String? = null,
    var city: String? = null,
    var state: String? = null,
    var country: String? = null,
    var postalCode: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(street)
        parcel.writeString(extern)
        parcel.writeString(intern)
        parcel.writeString(colony)
        parcel.writeString(city)
        parcel.writeString(state)
        parcel.writeString(country)
        parcel.writeString(postalCode)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Address> {
        override fun createFromParcel(parcel: Parcel): Address {
            return Address(parcel)
        }

        override fun newArray(size: Int): Array<Address?> {
            return arrayOfNulls(size)
        }
    }
}