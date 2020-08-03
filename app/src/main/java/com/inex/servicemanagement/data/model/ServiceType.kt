package com.inex.servicemanagement.data.model


import android.os.Parcel
import android.os.Parcelable


data class ServiceType(
    val id: Long? = null,
    val isActive: Boolean = false,
    val description: String? = null,
    val price: Double,
    val currency: String? = null):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeByte(if (isActive) 1 else 0)
        parcel.writeString(description)
        parcel.writeDouble(price)
        parcel.writeString(currency)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ServiceType> {
        override fun createFromParcel(parcel: Parcel): ServiceType {
            return ServiceType(parcel)
        }

        override fun newArray(size: Int): Array<ServiceType?> {
            return arrayOfNulls(size)
        }
    }

}