package com.example.movieslist.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FavoriteMovie(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: String = "",

    @ColumnInfo(name = "judul")
    var judul: String = "",

    @ColumnInfo(name = "sinopsis")
    var sinopsis: String = "",

    @ColumnInfo(name = "tanggalRilis")
    var tanggalRilis: String = "",

    @ColumnInfo(name = "poster")
    var poster: String = ""

) : Parcelable
