package com.example.aprendendoandroid.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Entity
data class Usuarios(
    @PrimaryKey
    val id: String,
    val nome: String,
    val senha: String
)
