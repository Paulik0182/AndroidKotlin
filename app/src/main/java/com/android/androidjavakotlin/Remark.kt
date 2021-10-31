package com.android.androidjavakotlin

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable

class Remark : Parcelable{
    private var name: String? = null
    private var description: String? = null
    private var date: String? = null

    fun Remark(name: String?, description: String?, date: String?) {
        this.name = name
        this.description = description
        this.date = date
    }

    protected fun Remark(`in`: Parcel) {
        name = `in`.readString()
        description = `in`.readString()
        date = `in`.readString()
    }

    @SuppressLint("ParcelCreator")
    val CREATOR: Parcelable.Creator<Remark?> = object : Parcelable.Creator<Remark?> {
        override fun createFromParcel(`in`: Parcel): Remark? {
            return com.android.androidjavakotlin.Remark(`in`)
        }

        override fun newArray(size: Int): Array<Remark?> {
            return arrayOfNulls(size)
        }
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getDescription(): String? {
        return description
    }

    fun setDescription(description: String?) {
        this.description = description
    }

    fun getDate(): String? {
        return date
    }

    fun setDate(date: String?) {
        this.date = date
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeString(description)
        dest.writeString(date)
    }
}