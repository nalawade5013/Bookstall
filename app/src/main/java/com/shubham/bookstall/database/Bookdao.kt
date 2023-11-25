package com.shubham.bookstall.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface Bookdao {

    @Insert
    fun insertbook(bookenitites: Bookenitites)

    @Delete
    fun deletbook(bookenitites: Bookenitites)


    @Query("SELECT * FROM books")
    fun getallbooks():List<Bookenitites>


    @Query("SELECT * FROM books WHERE book_id= :bookid")
    fun getbookid(bookid : String): Bookenitites


}