package com.example.aprendendoandroid.extensions

import android.content.Context
import android.content.Intent

fun Context.vaiPara(clazz: Class<*>) {
    Intent(this, clazz)
        .apply {
            startActivity(this)
        }
}