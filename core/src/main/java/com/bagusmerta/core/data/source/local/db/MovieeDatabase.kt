package com.bagusmerta.core.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bagusmerta.core.data.source.local.dao.MovieeDao
import com.bagusmerta.core.data.source.local.entity.MovieeEntity

@Database(entities = [MovieeEntity::class], version = 1, exportSchema = false)
abstract class MovieeDatabase: RoomDatabase() {
    abstract fun movieeDao(): MovieeDao
}