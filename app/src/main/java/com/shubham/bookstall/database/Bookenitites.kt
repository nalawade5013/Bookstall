package com.shubham.bookstall.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="books")
 data class Bookenitites(

   @PrimaryKey val book_id : Int,
   @ColumnInfo(name = "book_name") val Bookname : String,
   @ColumnInfo(name = "book_authorname") val Bookauthorname : String,
   @ColumnInfo(name="book_rating") val  Bookrating : String,
   @ColumnInfo(name="book_price") val Bookprice : String,
   @ColumnInfo(name="book_des") val Bookdes : String,
   @ColumnInfo(name="book_image") val image : String,
 )
