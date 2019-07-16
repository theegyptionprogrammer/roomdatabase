package com.example.roompractice

import android.arch.persistence.room.TypeConverter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.TextUtils
import android.util.Base64
import java.io.ByteArrayOutputStream


class Converters {


        @TypeConverter
        fun fromBase64ToBitmap(base64Value: String): Bitmap? {
            if (!TextUtils.isEmpty(base64Value)) {
                val decodedBytes = Base64.decode(base64Value, 0)
                return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
            } else {
                return null
            }
        }

        @TypeConverter
        fun fromBitmapToBase64(bitmap: Bitmap?): String? {
            if (bitmap != null) {
                val byteArrayOS = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 25, byteArrayOS)
                return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT)
            } else {
                return null
            }
        }

    companion object{
        @TypeConverter
        @JvmStatic
        fun fromValueToBitmap(value : String): Bitmap?{
            return value.
        }
    }




}