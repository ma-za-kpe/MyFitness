package com.maku.myfitness.core.data.offline.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout")
data class WorkOut(
    @PrimaryKey
    @ColumnInfo(name = "ID") val ID: Int,
    @ColumnInfo(name = "Name") val Name: String,
    @ColumnInfo(name = "Excrcise_Name") val Excrcise_Name: String,
    @ColumnInfo(name = "Excrcise_Image_Name")  val Excrcise_Image_Name: String,
    @ColumnInfo(name = "Excrcise_Image_Count") val Excrcise_Image_Count: String,
    @ColumnInfo(name = "Excrcise_Note") val Excrcise_Note: String,
    @ColumnInfo(name = "Excrcise_Link") val Excrcise_Link: String,
)