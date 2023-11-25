package com.shubham.bookstall.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Bookenitites::class], version = 1)
abstract class Bookdatabase : RoomDatabase() {

    abstract fun bookdao():Bookdao
}