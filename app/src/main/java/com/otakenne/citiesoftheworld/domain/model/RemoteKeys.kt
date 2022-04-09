package com.otakenne.citiesoftheworld.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys_table")
data class RemoteKeys(
    @PrimaryKey
    val cityID: Long,
    val previousKey: Int?,
    val nextKey: Int?
)
