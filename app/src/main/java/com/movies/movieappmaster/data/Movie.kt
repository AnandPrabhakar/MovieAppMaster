package com.movies.movieappmaster.data
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Movies")
data class Movie(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long,
                 @field:SerializedName("Poster") val image_url: String,
    @field:SerializedName("Title") val name: String,
    @field:SerializedName("Year") val year: String)


